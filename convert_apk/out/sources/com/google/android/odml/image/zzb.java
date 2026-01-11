package com.google.android.odml.image;

/* compiled from: com.google.android.odml:image@@1.0.0-beta1 */
final class zzb extends zzh {
    private Integer zza;
    private Integer zzb;

    zzb() {
    }

    /* access modifiers changed from: package-private */
    public final zzh zza(int i) {
        this.zza = Integer.valueOf(i);
        return this;
    }

    /* access modifiers changed from: package-private */
    public final zzh zzb(int i) {
        this.zzb = Integer.valueOf(i);
        return this;
    }

    /* access modifiers changed from: package-private */
    public final ImageProperties zzc() {
        Integer num = this.zza;
        if (num != null && this.zzb != null) {
            return new zzc(num.intValue(), this.zzb.intValue(), (zza) null);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" imageFormat");
        }
        if (this.zzb == null) {
            sb.append(" storageType");
        }
        String valueOf = String.valueOf(sb);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 28);
        sb2.append("Missing required properties:");
        sb2.append(valueOf);
        throw new IllegalStateException(sb2.toString());
    }
}
