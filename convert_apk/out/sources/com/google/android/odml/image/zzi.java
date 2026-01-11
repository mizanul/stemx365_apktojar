package com.google.android.odml.image;

import android.media.Image;
import android.os.Build;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
final class zzi implements zzg {
    private final Image zza;
    private final ImageProperties zzb;

    public zzi(Image image) {
        int i;
        this.zza = image;
        zzb zzb2 = new zzb();
        zzb2.zzb(3);
        int format = image.getFormat();
        if (Build.VERSION.SDK_INT >= 23) {
            if (format == 42) {
                i = 1;
            } else if (format == 41) {
                i = 2;
            }
            zzb2.zza(i);
            this.zzb = zzb2.zzc();
        }
        i = format != 35 ? format != 256 ? 0 : 9 : 7;
        zzb2.zza(i);
        this.zzb = zzb2.zzc();
    }

    public final Image zza() {
        return this.zza;
    }

    public final ImageProperties zzb() {
        return this.zzb;
    }

    public final void zzc() {
        this.zza.close();
    }
}
