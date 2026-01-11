package com.google.android.odml.image;

import android.graphics.Bitmap;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
final class zze implements zzg {
    private final Bitmap zza;
    private final ImageProperties zzb;

    public zze(Bitmap bitmap) {
        int i;
        this.zza = bitmap;
        zzb zzb2 = new zzb();
        int i2 = zzd.zza[bitmap.getConfig().ordinal()];
        if (i2 != 1) {
            i = i2 != 2 ? 0 : 1;
        } else {
            i = 8;
        }
        zzb2.zza(i);
        zzb2.zzb(1);
        this.zzb = zzb2.zzc();
    }

    public final Bitmap zza() {
        return this.zza;
    }

    public final ImageProperties zzb() {
        return this.zzb;
    }

    public final void zzc() {
        this.zza.recycle();
    }
}
