package com.google.android.odml.image;

import android.graphics.Rect;
import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
public class MlImage implements Closeable {
    public static final int IMAGE_FORMAT_ALPHA = 8;
    public static final int IMAGE_FORMAT_JPEG = 9;
    public static final int IMAGE_FORMAT_NV12 = 3;
    public static final int IMAGE_FORMAT_NV21 = 4;
    public static final int IMAGE_FORMAT_RGB = 2;
    public static final int IMAGE_FORMAT_RGBA = 1;
    public static final int IMAGE_FORMAT_UNKNOWN = 0;
    public static final int IMAGE_FORMAT_YUV_420_888 = 7;
    public static final int IMAGE_FORMAT_YV12 = 5;
    public static final int IMAGE_FORMAT_YV21 = 6;
    public static final int STORAGE_TYPE_BITMAP = 1;
    public static final int STORAGE_TYPE_BYTEBUFFER = 2;
    public static final int STORAGE_TYPE_MEDIA_IMAGE = 3;
    private final zzg zza;
    private final int zzb;
    private final Rect zzc;
    private final int zzd;
    private final int zze;
    private int zzf = 1;

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
    public @interface ImageFormat {
    }

    /* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
    public static final class Internal {
        private final MlImage zza;

        /* synthetic */ Internal(MlImage mlImage, zzj zzj) {
            this.zza = mlImage;
        }

        public void acquire() {
            this.zza.zzd();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
    public @interface StorageType {
    }

    MlImage(zzg zzg, int i, Rect rect, long j, int i2, int i3) {
        this.zza = zzg;
        this.zzb = i;
        Rect rect2 = new Rect();
        this.zzc = rect2;
        rect2.set(rect);
        this.zzd = i2;
        this.zze = i3;
    }

    static void zzc(int i) {
        if (i != 0 && i != 90 && i != 180 && i != 270) {
            StringBuilder sb = new StringBuilder(68);
            sb.append("Rotation value ");
            sb.append(i);
            sb.append(" is not valid. Use only 0, 90, 180 or 270.");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public final synchronized void zzd() {
        this.zzf++;
    }

    public synchronized void close() {
        int i = this.zzf - 1;
        this.zzf = i;
        if (i == 0) {
            this.zza.zzc();
        }
    }

    public List<ImageProperties> getContainedImageProperties() {
        return Collections.singletonList(this.zza.zzb());
    }

    public int getHeight() {
        return this.zze;
    }

    public Internal getInternal() {
        return new Internal(this, (zzj) null);
    }

    public int getRotation() {
        return this.zzb;
    }

    public int getWidth() {
        return this.zzd;
    }

    /* access modifiers changed from: package-private */
    public final zzg zza() {
        return this.zza;
    }
}
