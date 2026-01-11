package android.support.p002v4.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.p002v4.content.res.FontResourcesParserCompat;
import android.support.p002v4.graphics.TypefaceCompat;
import android.support.p002v4.graphics.TypefaceCompatUtil;
import android.support.p002v4.provider.SelfDestructiveThread;
import android.support.p002v4.util.LruCache;
import android.support.p002v4.util.Preconditions;
import android.support.p002v4.util.SimpleArrayMap;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.opencv.videoio.Videoio;

/* renamed from: android.support.v4.provider.FontsContractCompat */
public class FontsContractCompat {
    private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
    public static final String PARCEL_FONT_RESULTS = "font_results";
    public static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
    public static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
    private static final String TAG = "FontsContractCompat";
    private static final SelfDestructiveThread sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() {
        public int compare(byte[] l, byte[] r) {
            if (l.length != r.length) {
                return l.length - r.length;
            }
            for (int i = 0; i < l.length; i++) {
                if (l[i] != r[i]) {
                    return l[i] - r[i];
                }
            }
            return 0;
        }
    };
    /* access modifiers changed from: private */
    public static final Object sLock = new Object();
    /* access modifiers changed from: private */
    public static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>>> sPendingReplies = new SimpleArrayMap<>();
    /* access modifiers changed from: private */
    public static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);

    /* renamed from: android.support.v4.provider.FontsContractCompat$Columns */
    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    private FontsContractCompat() {
    }

    /* access modifiers changed from: private */
    public static Typeface getFontInternal(Context context, FontRequest request, int style) {
        try {
            FontFamilyResult result = fetchFonts(context, (CancellationSignal) null, request);
            if (result.getStatusCode() == 0) {
                return TypefaceCompat.createFromFontInfo(context, (CancellationSignal) null, result.getFonts(), style);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Typeface getFontSync(final Context context, final FontRequest request, final TextView targetView, int strategy, int timeout, final int style) {
        final String id = request.getIdentifier() + "-" + style;
        Typeface cached = sTypefaceCache.get(id);
        if (cached != null) {
            return cached;
        }
        boolean isBlockingFetch = strategy == 0;
        if (isBlockingFetch && timeout == -1) {
            return getFontInternal(context, request, style);
        }
        Callable<Typeface> fetcher = new Callable<Typeface>() {
            public Typeface call() throws Exception {
                Typeface typeface = FontsContractCompat.getFontInternal(context, request, style);
                if (typeface != null) {
                    FontsContractCompat.sTypefaceCache.put(id, typeface);
                }
                return typeface;
            }
        };
        if (isBlockingFetch) {
            try {
                return (Typeface) sBackgroundThread.postAndWait(fetcher, timeout);
            } catch (InterruptedException e) {
                return null;
            }
        } else {
            final WeakReference<TextView> textViewWeak = new WeakReference<>(targetView);
            SelfDestructiveThread.ReplyCallback<Typeface> reply = new SelfDestructiveThread.ReplyCallback<Typeface>() {
                public void onReply(Typeface typeface) {
                    if (((TextView) textViewWeak.get()) != null) {
                        targetView.setTypeface(typeface, style);
                    }
                }
            };
            synchronized (sLock) {
                if (sPendingReplies.containsKey(id)) {
                    sPendingReplies.get(id).add(reply);
                    return null;
                }
                ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>> pendingReplies = new ArrayList<>();
                pendingReplies.add(reply);
                sPendingReplies.put(id, pendingReplies);
                sBackgroundThread.postAndReply(fetcher, new SelfDestructiveThread.ReplyCallback<Typeface>() {
                    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
                        if (r0 >= r1.size()) goto L_0x0030;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
                        ((android.support.p002v4.provider.SelfDestructiveThread.ReplyCallback) r1.get(r0)).onReply(r5);
                        r0 = r0 + 1;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0030, code lost:
                        return;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
                        r0 = 0;
                     */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void onReply(android.graphics.Typeface r5) {
                        /*
                            r4 = this;
                            java.lang.Object r0 = android.support.p002v4.provider.FontsContractCompat.sLock
                            monitor-enter(r0)
                            r1 = 0
                            android.support.v4.util.SimpleArrayMap r2 = android.support.p002v4.provider.FontsContractCompat.sPendingReplies     // Catch:{ all -> 0x0031 }
                            java.lang.String r3 = r0     // Catch:{ all -> 0x0031 }
                            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0031 }
                            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch:{ all -> 0x0031 }
                            r1 = r2
                            android.support.v4.util.SimpleArrayMap r2 = android.support.p002v4.provider.FontsContractCompat.sPendingReplies     // Catch:{ all -> 0x0034 }
                            java.lang.String r3 = r0     // Catch:{ all -> 0x0034 }
                            r2.remove(r3)     // Catch:{ all -> 0x0034 }
                            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
                            r0 = 0
                        L_0x001e:
                            int r2 = r1.size()
                            if (r0 >= r2) goto L_0x0030
                            java.lang.Object r2 = r1.get(r0)
                            android.support.v4.provider.SelfDestructiveThread$ReplyCallback r2 = (android.support.p002v4.provider.SelfDestructiveThread.ReplyCallback) r2
                            r2.onReply(r5)
                            int r0 = r0 + 1
                            goto L_0x001e
                        L_0x0030:
                            return
                        L_0x0031:
                            r2 = move-exception
                        L_0x0032:
                            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
                            throw r2
                        L_0x0034:
                            r2 = move-exception
                            goto L_0x0032
                        */
                        throw new UnsupportedOperationException("Method not decompiled: android.support.p002v4.provider.FontsContractCompat.C01993.onReply(android.graphics.Typeface):void");
                    }
                });
                return null;
            }
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontInfo */
    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        public FontInfo(Uri uri, int ttcIndex, int weight, boolean italic, int resultCode) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = ttcIndex;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResultCode = resultCode;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontFamilyResult */
    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: android.support.v4.provider.FontsContractCompat$FontFamilyResult$FontResultStatus */
        @interface FontResultStatus {
        }

        public FontFamilyResult(int statusCode, FontInfo[] fonts) {
            this.mStatusCode = statusCode;
            this.mFonts = fonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontRequestCallback */
    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: android.support.v4.provider.FontsContractCompat$FontRequestCallback$FontRequestFailReason */
        @interface FontRequestFailReason {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        public void onTypefaceRequestFailed(int reason) {
        }
    }

    public static void requestFont(final Context context, final FontRequest request, final FontRequestCallback callback, Handler handler) {
        final Handler callerThreadHandler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                try {
                    FontFamilyResult result = FontsContractCompat.fetchFonts(context, (CancellationSignal) null, request);
                    if (result.getStatusCode() != 0) {
                        int statusCode = result.getStatusCode();
                        if (statusCode == 1) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-2);
                                }
                            });
                        } else if (statusCode != 2) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-3);
                                }
                            });
                        } else {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-3);
                                }
                            });
                        }
                    } else {
                        FontInfo[] fonts = result.getFonts();
                        if (fonts == null || fonts.length == 0) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(1);
                                }
                            });
                            return;
                        }
                        int length = fonts.length;
                        int i = 0;
                        while (i < length) {
                            FontInfo font = fonts[i];
                            if (font.getResultCode() != 0) {
                                final int resultCode = font.getResultCode();
                                if (resultCode < 0) {
                                    callerThreadHandler.post(new Runnable() {
                                        public void run() {
                                            callback.onTypefaceRequestFailed(-3);
                                        }
                                    });
                                    return;
                                } else {
                                    callerThreadHandler.post(new Runnable() {
                                        public void run() {
                                            callback.onTypefaceRequestFailed(resultCode);
                                        }
                                    });
                                    return;
                                }
                            } else {
                                i++;
                            }
                        }
                        final Typeface typeface = FontsContractCompat.buildTypeface(context, (CancellationSignal) null, fonts);
                        if (typeface == null) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-3);
                                }
                            });
                        } else {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRetrieved(typeface);
                                }
                            });
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    callerThreadHandler.post(new Runnable() {
                        public void run() {
                            callback.onTypefaceRequestFailed(-1);
                        }
                    });
                }
            }
        });
    }

    public static Typeface buildTypeface(Context context, CancellationSignal cancellationSignal, FontInfo[] fonts) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fonts, 0);
    }

    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap<>();
        for (FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    out.put(uri, TypefaceCompatUtil.mmap(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest request) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return new FontFamilyResult(1, (FontInfo[]) null);
        }
        return new FontFamilyResult(0, getFontFromProvider(context, request, providerInfo.authority, cancellationSignal));
    }

    public static ProviderInfo getProvider(PackageManager packageManager, FontRequest request, Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (info.packageName.equals(request.getProviderPackage())) {
            List<byte[]> signatures = convertToByteArrayList(packageManager.getPackageInfo(info.packageName, 64).signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, request.getCertificatesArrayResId());
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shas = new ArrayList<>();
        for (Signature byteArray : signatures) {
            shas.add(byteArray.toByteArray());
        }
        return shas;
    }

    static FontInfo[] getFontFromProvider(Context context, FontRequest request, String authority, CancellationSignal cancellationSignal) {
        Uri fileUri;
        Cursor cursor;
        String str = authority;
        ArrayList<FontInfo> result = new ArrayList<>();
        Uri uri = new Uri.Builder().scheme("content").authority(str).build();
        Uri fileBaseUri = new Uri.Builder().scheme("content").authority(str).appendPath(HttpPostBodyUtil.FILE).build();
        Cursor cursor2 = null;
        try {
            int i = 0;
            if (Build.VERSION.SDK_INT > 16) {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{request.getQuery()}, (String) null, cancellationSignal);
            } else {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{request.getQuery()}, (String) null);
            }
            if (cursor2 != null && cursor2.getCount() > 0) {
                int resultCodeColumnIndex = cursor2.getColumnIndex(Columns.RESULT_CODE);
                result = new ArrayList<>();
                int idColumnIndex = cursor2.getColumnIndex("_id");
                int fileIdColumnIndex = cursor2.getColumnIndex(Columns.FILE_ID);
                int ttcIndexColumnIndex = cursor2.getColumnIndex(Columns.TTC_INDEX);
                int weightColumnIndex = cursor2.getColumnIndex(Columns.WEIGHT);
                int italicColumnIndex = cursor2.getColumnIndex(Columns.ITALIC);
                while (cursor2.moveToNext()) {
                    int resultCode = resultCodeColumnIndex != -1 ? cursor2.getInt(resultCodeColumnIndex) : i;
                    int ttcIndex = ttcIndexColumnIndex != -1 ? cursor2.getInt(ttcIndexColumnIndex) : i;
                    if (fileIdColumnIndex == -1) {
                        fileUri = ContentUris.withAppendedId(uri, cursor2.getLong(idColumnIndex));
                    } else {
                        fileUri = ContentUris.withAppendedId(fileBaseUri, cursor2.getLong(fileIdColumnIndex));
                    }
                    result.add(new FontInfo(fileUri, ttcIndex, weightColumnIndex != -1 ? cursor2.getInt(weightColumnIndex) : Videoio.CAP_PROP_XI_DOWNSAMPLING, italicColumnIndex != -1 && cursor2.getInt(italicColumnIndex) == 1, resultCode));
                    i = 0;
                }
            }
            return (FontInfo[]) result.toArray(new FontInfo[0]);
        } finally {
            if (cursor2 != null) {
                cursor2.close();
            }
        }
    }
}
