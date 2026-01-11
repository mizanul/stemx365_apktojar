package com.google.android.odml.image;

import android.graphics.Rect;
import java.nio.ByteBuffer;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
public class ByteBufferMlImageBuilder {
    private final ByteBuffer zza;
    private final int zzb;
    private final int zzc;
    private final int zzd;
    private int zze = 0;
    private Rect zzf;

    public ByteBufferMlImageBuilder(ByteBuffer byteBuffer, int width, int height, int imageFormat) {
        this.zza = byteBuffer;
        this.zzb = width;
        this.zzc = height;
        this.zzd = imageFormat;
        this.zzf = new Rect(0, 0, width, height);
    }

    public MlImage build() {
        return new MlImage(new zzf(this.zza, this.zzd), this.zze, this.zzf, 0, this.zzb, this.zzc);
    }

    public ByteBufferMlImageBuilder setRotation(int rotation) {
        MlImage.zzc(rotation);
        this.zze = rotation;
        return this;
    }
}
