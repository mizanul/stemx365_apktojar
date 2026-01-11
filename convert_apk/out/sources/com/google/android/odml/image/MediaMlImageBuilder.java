package com.google.android.odml.image;

import android.graphics.Rect;
import android.media.Image;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
public class MediaMlImageBuilder {
    private final Image zza;
    private int zzb = 0;
    private Rect zzc;

    public MediaMlImageBuilder(Image mediaImage) {
        this.zza = mediaImage;
        this.zzc = new Rect(0, 0, mediaImage.getWidth(), mediaImage.getHeight());
    }

    public MlImage build() {
        return new MlImage(new zzi(this.zza), this.zzb, this.zzc, 0, this.zza.getWidth(), this.zza.getHeight());
    }

    public MediaMlImageBuilder setRotation(int rotation) {
        MlImage.zzc(rotation);
        this.zzb = rotation;
        return this;
    }
}
