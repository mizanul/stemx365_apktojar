package org.tensorflow.lite.support.common;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;

public class FileUtil {
    private FileUtil() {
    }

    public static List<String> loadLabels(Context context, String filePath) throws IOException {
        return loadLabels(context, filePath, Charset.defaultCharset());
    }

    public static List<String> loadLabels(Context context, String filePath, Charset cs) throws IOException {
        SupportPreconditions.checkNotNull(context, "Context cannot be null.");
        SupportPreconditions.checkNotNull(filePath, "File path cannot be null.");
        InputStream inputStream = context.getAssets().open(filePath);
        try {
            List<String> loadLabels = loadLabels(inputStream, cs);
            if (inputStream != null) {
                inputStream.close();
            }
            return loadLabels;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static List<String> loadLabels(InputStream inputStream) throws IOException {
        return loadLabels(inputStream, Charset.defaultCharset());
    }

    public static List<String> loadLabels(InputStream inputStream, Charset cs) throws IOException {
        List<String> labels = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, cs));
        while (true) {
            try {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine == null) {
                    reader.close();
                    return labels;
                } else if (line.trim().length() > 0) {
                    labels.add(line);
                }
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
        }
        throw th;
    }

    public static List<String> loadSingleColumnTextFile(Context context, String filePath, Charset cs) throws IOException {
        return loadLabels(context, filePath, cs);
    }

    public static List<String> loadSingleColumnTextFile(InputStream inputStream, Charset cs) throws IOException {
        return loadLabels(inputStream, cs);
    }

    public static MappedByteBuffer loadMappedFile(Context context, String filePath) throws IOException {
        FileInputStream inputStream;
        SupportPreconditions.checkNotNull(context, "Context should not be null.");
        SupportPreconditions.checkNotNull(filePath, "File path cannot be null.");
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(filePath);
        try {
            inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            MappedByteBuffer map = inputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
            inputStream.close();
            if (fileDescriptor != null) {
                fileDescriptor.close();
            }
            return map;
        } catch (Throwable th) {
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
        throw th;
    }

    public static byte[] loadByteFromFile(Context context, String filePath) throws IOException {
        ByteBuffer buffer = loadMappedFile(context, filePath);
        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);
        return byteArray;
    }
}
