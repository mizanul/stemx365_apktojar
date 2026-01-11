package android.support.p002v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.support.p002v4.content.res.FontResourcesParserCompat;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* renamed from: android.support.v4.graphics.TypefaceCompatApi26Impl */
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    static {
        Method abortCreationMethod;
        Method freezeMethod;
        Method addFromBufferMethod;
        Method addFontMethod;
        Constructor fontFamilyCtor;
        Class fontFamilyClass;
        Method createFromFamiliesWithDefaultMethod;
        try {
            fontFamilyClass = Class.forName(FONT_FAMILY_CLASS);
            try {
                fontFamilyCtor = fontFamilyClass.getConstructor(new Class[0]);
            } catch (ClassNotFoundException e) {
                e = e;
                Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                addFromBufferMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = addFromBufferMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
            } catch (NoSuchMethodException e2) {
                e = e2;
                Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                addFromBufferMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = addFromBufferMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
            }
            try {
                addFontMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
            } catch (ClassNotFoundException e3) {
                e = e3;
                Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                addFromBufferMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = addFromBufferMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
            } catch (NoSuchMethodException e4) {
                e = e4;
                Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                addFromBufferMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = addFromBufferMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
            }
            try {
                addFromBufferMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_BUFFER_METHOD, new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
                try {
                    freezeMethod = fontFamilyClass.getMethod(FREEZE_METHOD, new Class[0]);
                } catch (ClassNotFoundException e5) {
                    e = e5;
                    Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    addFromBufferMethod = null;
                    freezeMethod = null;
                    abortCreationMethod = null;
                    createFromFamiliesWithDefaultMethod = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontFromAssetManager = addFontMethod;
                    sAddFontFromBuffer = addFromBufferMethod;
                    sFreeze = freezeMethod;
                    sAbortCreation = abortCreationMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
                } catch (NoSuchMethodException e6) {
                    e = e6;
                    Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    addFromBufferMethod = null;
                    freezeMethod = null;
                    abortCreationMethod = null;
                    createFromFamiliesWithDefaultMethod = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontFromAssetManager = addFontMethod;
                    sAddFontFromBuffer = addFromBufferMethod;
                    sFreeze = freezeMethod;
                    sAbortCreation = abortCreationMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
                }
                try {
                    abortCreationMethod = fontFamilyClass.getMethod(ABORT_CREATION_METHOD, new Class[0]);
                    try {
                        createFromFamiliesWithDefaultMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{Array.newInstance(fontFamilyClass, 1).getClass(), Integer.TYPE, Integer.TYPE});
                        try {
                            createFromFamiliesWithDefaultMethod.setAccessible(true);
                        } catch (ClassNotFoundException | NoSuchMethodException e7) {
                            e = e7;
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException e8) {
                        e = e8;
                        Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                        fontFamilyClass = null;
                        fontFamilyCtor = null;
                        addFontMethod = null;
                        addFromBufferMethod = null;
                        freezeMethod = null;
                        abortCreationMethod = null;
                        createFromFamiliesWithDefaultMethod = null;
                        sFontFamilyCtor = fontFamilyCtor;
                        sFontFamily = fontFamilyClass;
                        sAddFontFromAssetManager = addFontMethod;
                        sAddFontFromBuffer = addFromBufferMethod;
                        sFreeze = freezeMethod;
                        sAbortCreation = abortCreationMethod;
                        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
                    }
                } catch (ClassNotFoundException e9) {
                    e = e9;
                    Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    addFromBufferMethod = null;
                    freezeMethod = null;
                    abortCreationMethod = null;
                    createFromFamiliesWithDefaultMethod = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontFromAssetManager = addFontMethod;
                    sAddFontFromBuffer = addFromBufferMethod;
                    sFreeze = freezeMethod;
                    sAbortCreation = abortCreationMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
                } catch (NoSuchMethodException e10) {
                    e = e10;
                    Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    addFromBufferMethod = null;
                    freezeMethod = null;
                    abortCreationMethod = null;
                    createFromFamiliesWithDefaultMethod = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontFromAssetManager = addFontMethod;
                    sAddFontFromBuffer = addFromBufferMethod;
                    sFreeze = freezeMethod;
                    sAbortCreation = abortCreationMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
                }
            } catch (ClassNotFoundException e11) {
                e = e11;
                Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                addFromBufferMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = addFromBufferMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
            } catch (NoSuchMethodException e12) {
                e = e12;
                Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                addFromBufferMethod = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = addFromBufferMethod;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
            }
        } catch (ClassNotFoundException e13) {
            e = e13;
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            fontFamilyClass = null;
            fontFamilyCtor = null;
            addFontMethod = null;
            addFromBufferMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod = null;
            sFontFamilyCtor = fontFamilyCtor;
            sFontFamily = fontFamilyClass;
            sAddFontFromAssetManager = addFontMethod;
            sAddFontFromBuffer = addFromBufferMethod;
            sFreeze = freezeMethod;
            sAbortCreation = abortCreationMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
        } catch (NoSuchMethodException e14) {
            e = e14;
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            fontFamilyClass = null;
            fontFamilyCtor = null;
            addFontMethod = null;
            addFromBufferMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod = null;
            sFontFamilyCtor = fontFamilyCtor;
            sFontFamily = fontFamilyClass;
            sAddFontFromAssetManager = addFontMethod;
            sAddFontFromBuffer = addFromBufferMethod;
            sFreeze = freezeMethod;
            sAbortCreation = abortCreationMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
        }
        sFontFamilyCtor = fontFamilyCtor;
        sFontFamily = fontFamilyClass;
        sAddFontFromAssetManager = addFontMethod;
        sAddFontFromBuffer = addFromBufferMethod;
        sFreeze = freezeMethod;
        sAbortCreation = abortCreationMethod;
        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontFromAssetManager != null;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromAssetManager.invoke(family, new Object[]{context.getAssets(), fileName, 0, false, Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), null})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromBuffer.invoke(family, new Object[]{buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style)})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance(sFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke((Object) null, new Object[]{familyArray, -1, -1});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean freeze(Object family) {
        try {
            return ((Boolean) sFreeze.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean abortCreation(Object family) {
        try {
            return ((Boolean) sAbortCreation.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFile : entry.getEntries()) {
            if (!addFontFromAssetManager(context, fontFamily, fontFile.getFileName(), 0, fontFile.getWeight(), fontFile.isItalic() ? 1 : 0)) {
                abortCreation(fontFamily);
                return null;
            }
        }
        if (!freeze(fontFamily)) {
            return null;
        }
        return createFromFamiliesWithDefault(fontFamily);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0045, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
        if (r3 != null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0050, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r12, android.os.CancellationSignal r13, android.support.p002v4.provider.FontsContractCompat.FontInfo[] r14, int r15) {
        /*
            r11 = this;
            int r0 = r14.length
            r1 = 1
            r2 = 0
            if (r0 >= r1) goto L_0x0006
            return r2
        L_0x0006:
            boolean r0 = isFontFamilyPrivateAPIAvailable()
            if (r0 != 0) goto L_0x0053
            android.support.v4.provider.FontsContractCompat$FontInfo r0 = r11.findBestInfo(r14, r15)
            android.content.ContentResolver r1 = r12.getContentResolver()
            android.net.Uri r3 = r0.getUri()     // Catch:{ IOException -> 0x0051 }
            java.lang.String r4 = "r"
            android.os.ParcelFileDescriptor r3 = r1.openFileDescriptor(r3, r4, r13)     // Catch:{ IOException -> 0x0051 }
            android.graphics.Typeface$Builder r4 = new android.graphics.Typeface$Builder     // Catch:{ all -> 0x0043 }
            java.io.FileDescriptor r5 = r3.getFileDescriptor()     // Catch:{ all -> 0x0043 }
            r4.<init>(r5)     // Catch:{ all -> 0x0043 }
            int r5 = r0.getWeight()     // Catch:{ all -> 0x0043 }
            android.graphics.Typeface$Builder r4 = r4.setWeight(r5)     // Catch:{ all -> 0x0043 }
            boolean r5 = r0.isItalic()     // Catch:{ all -> 0x0043 }
            android.graphics.Typeface$Builder r4 = r4.setItalic(r5)     // Catch:{ all -> 0x0043 }
            android.graphics.Typeface r4 = r4.build()     // Catch:{ all -> 0x0043 }
            if (r3 == 0) goto L_0x0042
            r3.close()     // Catch:{ IOException -> 0x0051 }
        L_0x0042:
            return r4
        L_0x0043:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0045 }
        L_0x0045:
            r5 = move-exception
            if (r3 == 0) goto L_0x0050
            r3.close()     // Catch:{ all -> 0x004c }
            goto L_0x0050
        L_0x004c:
            r6 = move-exception
            r4.addSuppressed(r6)     // Catch:{ IOException -> 0x0051 }
        L_0x0050:
            throw r5     // Catch:{ IOException -> 0x0051 }
        L_0x0051:
            r3 = move-exception
            return r2
        L_0x0053:
            java.util.Map r0 = android.support.p002v4.provider.FontsContractCompat.prepareFontData(r12, r14, r13)
            java.lang.Object r1 = newFamily()
            r3 = 0
            int r4 = r14.length
            r5 = 0
        L_0x005e:
            if (r5 >= r4) goto L_0x008a
            r6 = r14[r5]
            android.net.Uri r7 = r6.getUri()
            java.lang.Object r7 = r0.get(r7)
            java.nio.ByteBuffer r7 = (java.nio.ByteBuffer) r7
            if (r7 != 0) goto L_0x006f
            goto L_0x0087
        L_0x006f:
            int r8 = r6.getTtcIndex()
            int r9 = r6.getWeight()
            boolean r10 = r6.isItalic()
            boolean r8 = addFontFromBuffer(r1, r7, r8, r9, r10)
            if (r8 != 0) goto L_0x0086
            abortCreation(r1)
            return r2
        L_0x0086:
            r3 = 1
        L_0x0087:
            int r5 = r5 + 1
            goto L_0x005e
        L_0x008a:
            if (r3 != 0) goto L_0x0090
            abortCreation(r1)
            return r2
        L_0x0090:
            boolean r4 = freeze(r1)
            if (r4 != 0) goto L_0x0097
            return r2
        L_0x0097:
            android.graphics.Typeface r2 = createFromFamiliesWithDefault(r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p002v4.graphics.TypefaceCompatApi26Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }

    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (!addFontFromAssetManager(context, fontFamily, path, 0, -1, -1)) {
            abortCreation(fontFamily);
            return null;
        } else if (!freeze(fontFamily)) {
            return null;
        } else {
            return createFromFamiliesWithDefault(fontFamily);
        }
    }
}
