package android.support.p002v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* renamed from: android.support.v4.graphics.TypefaceCompatUtil */
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        int i = 0;
        while (i < 100) {
            File file = new File(context.getCacheDir(), prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001b, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0024, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.nio.ByteBuffer mmap(java.io.File r7) {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0025 }
            r0.<init>(r7)     // Catch:{ IOException -> 0x0025 }
            java.nio.channels.FileChannel r1 = r0.getChannel()     // Catch:{ all -> 0x0019 }
            long r5 = r1.size()     // Catch:{ all -> 0x0019 }
            java.nio.channels.FileChannel$MapMode r2 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x0019 }
            r3 = 0
            java.nio.MappedByteBuffer r2 = r1.map(r2, r3, r5)     // Catch:{ all -> 0x0019 }
            r0.close()     // Catch:{ IOException -> 0x0025 }
            return r2
        L_0x0019:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001b }
        L_0x001b:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0020 }
            goto L_0x0024
        L_0x0020:
            r3 = move-exception
            r1.addSuppressed(r3)     // Catch:{ IOException -> 0x0025 }
        L_0x0024:
            throw r2     // Catch:{ IOException -> 0x0025 }
        L_0x0025:
            r0 = move-exception
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p002v4.graphics.TypefaceCompatUtil.mmap(java.io.File):java.nio.ByteBuffer");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r3.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0038, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003c, code lost:
        if (r1 != null) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0046, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.nio.ByteBuffer mmap(android.content.Context r9, android.os.CancellationSignal r10, android.net.Uri r11) {
        /*
            android.content.ContentResolver r0 = r9.getContentResolver()
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r1 = r0.openFileDescriptor(r11, r1, r10)     // Catch:{ IOException -> 0x0047 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x0039 }
            java.io.FileDescriptor r3 = r1.getFileDescriptor()     // Catch:{ all -> 0x0039 }
            r2.<init>(r3)     // Catch:{ all -> 0x0039 }
            java.nio.channels.FileChannel r3 = r2.getChannel()     // Catch:{ all -> 0x002d }
            long r7 = r3.size()     // Catch:{ all -> 0x002d }
            java.nio.channels.FileChannel$MapMode r4 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x002d }
            r5 = 0
            java.nio.MappedByteBuffer r4 = r3.map(r4, r5, r7)     // Catch:{ all -> 0x002d }
            r2.close()     // Catch:{ all -> 0x0039 }
            if (r1 == 0) goto L_0x002c
            r1.close()     // Catch:{ IOException -> 0x0047 }
        L_0x002c:
            return r4
        L_0x002d:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x002f }
        L_0x002f:
            r4 = move-exception
            r2.close()     // Catch:{ all -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r5 = move-exception
            r3.addSuppressed(r5)     // Catch:{ all -> 0x0039 }
        L_0x0038:
            throw r4     // Catch:{ all -> 0x0039 }
        L_0x0039:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x003b }
        L_0x003b:
            r3 = move-exception
            if (r1 == 0) goto L_0x0046
            r1.close()     // Catch:{ all -> 0x0042 }
            goto L_0x0046
        L_0x0042:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ IOException -> 0x0047 }
        L_0x0046:
            throw r3     // Catch:{ IOException -> 0x0047 }
        L_0x0047:
            r1 = move-exception
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p002v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
    }

    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        ByteBuffer byteBuffer = null;
        if (tmpFile == null) {
            return null;
        }
        try {
            if (copyToFile(tmpFile, res, id)) {
                byteBuffer = mmap(tmpFile);
            }
            return byteBuffer;
        } finally {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                int readLen = read;
                if (read != -1) {
                    os.write(buffer, 0, readLen);
                } else {
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
            return false;
        } finally {
            closeQuietly(os);
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
