package com.google.android.odml.image;

import android.graphics.Bitmap;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
public final class BitmapExtractor {
    private BitmapExtractor() {
    }

    public static Bitmap extract(MlImage image) {
        zzg zza = image.zza();
        if (zza.zzb().getStorageType() == 1) {
            return ((zze) zza).zza();
        }
        throw new IllegalArgumentException("Extracting Bitmap from an MlImage created by objects other than Bitmap is not supported");
    }
}
