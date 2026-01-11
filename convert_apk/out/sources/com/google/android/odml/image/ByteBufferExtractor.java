package com.google.android.odml.image;

import java.nio.ByteBuffer;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
public class ByteBufferExtractor {
    private ByteBufferExtractor() {
    }

    public static ByteBuffer extract(MlImage image) {
        zzg zza = image.zza();
        if (zza.zzb().getStorageType() == 2) {
            return ((zzf) zza).zza().asReadOnlyBuffer();
        }
        throw new IllegalArgumentException("Extract ByteBuffer from an MlImage created by objects other than Bytebuffer is not supported");
    }
}
