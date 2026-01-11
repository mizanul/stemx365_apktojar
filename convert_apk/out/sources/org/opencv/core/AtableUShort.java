package org.opencv.core;

import kotlin.Metadata;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;
import org.opencv.core.Mat;

@Metadata(mo11628d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0018\u0010\u0010\u001a\u00020\u0002H\u0016ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u0011\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H\u0016ø\u0001\u0000J\u0011\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u0016H\u0016ø\u0001\u0000J\u0011\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0018H\u0016ø\u0001\u0000J\u001d\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016ø\u0001\u0000ø\u0001\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ\u0019\u0010\u001e\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H\u0016ø\u0001\u0000J\u0019\u0010\u001f\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0016H\u0016ø\u0001\u0000J\u0019\u0010 \u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0018H\u0016ø\u0001\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fø\u0001\u0000\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006!"}, mo11629d2 = {"Lorg/opencv/core/AtableUShort;", "Lorg/opencv/core/Mat$Atable;", "Lkotlin/UShort;", "mat", "Lorg/opencv/core/Mat;", "row", "", "col", "(Lorg/opencv/core/Mat;II)V", "indices", "", "(Lorg/opencv/core/Mat;[I)V", "getIndices", "()[I", "getMat", "()Lorg/opencv/core/Mat;", "getV", "getV-Mh2AYeg", "()S", "getV2c", "Lorg/opencv/core/Mat$Tuple2;", "getV3c", "Lorg/opencv/core/Mat$Tuple3;", "getV4c", "Lorg/opencv/core/Mat$Tuple4;", "setV", "", "v", "setV-xj2QHRw", "(S)V", "setV2c", "setV3c", "setV4c", "opencv-contrib_release"}, mo11630k = 1, mo11631mv = {1, 5, 1}, mo11633xi = 48)
/* compiled from: MatAt.kt */
public final class AtableUShort implements Mat.Atable<UShort> {
    private final int[] indices;
    private final Mat mat;

    public AtableUShort(Mat mat2, int[] indices2) {
        Intrinsics.checkNotNullParameter(mat2, "mat");
        Intrinsics.checkNotNullParameter(indices2, "indices");
        this.mat = mat2;
        this.indices = indices2;
    }

    public final int[] getIndices() {
        return this.indices;
    }

    public final Mat getMat() {
        return this.mat;
    }

    public /* bridge */ /* synthetic */ Object getV() {
        return UShort.m460boximpl(m1576getVMh2AYeg());
    }

    public /* bridge */ /* synthetic */ void setV(Object v) {
        m1577setVxj2QHRw(((UShort) v).m515unboximpl());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AtableUShort(Mat mat2, int row, int col) {
        this(mat2, new int[]{row, col});
        Intrinsics.checkNotNullParameter(mat2, "mat");
    }

    /* renamed from: getV-Mh2AYeg  reason: not valid java name */
    public short m1576getVMh2AYeg() {
        short[] data = UShortArray.m517constructorimpl(1);
        MatAtKt.m1580getN38XRpM(this.mat, this.indices, data);
        return UShortArray.m523getMh2AYeg(data, 0);
    }

    /* renamed from: setV-xj2QHRw  reason: not valid java name */
    public void m1577setVxj2QHRw(short v) {
        MatAtKt.m1584putN38XRpM(this.mat, this.indices, new short[]{v});
    }

    public Mat.Tuple2<UShort> getV2c() {
        short[] data = UShortArray.m517constructorimpl(2);
        MatAtKt.m1580getN38XRpM(this.mat, this.indices, data);
        return new Mat.Tuple2<>(UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 0)), UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 1)));
    }

    public void setV2c(Mat.Tuple2<UShort> v) {
        Intrinsics.checkNotNullParameter(v, "v");
        UShort _0 = v.get_0();
        Intrinsics.checkNotNullExpressionValue(_0, "v._0");
        UShort _1 = v.get_1();
        Intrinsics.checkNotNullExpressionValue(_1, "v._1");
        MatAtKt.m1584putN38XRpM(this.mat, this.indices, new short[]{_0.m515unboximpl(), _1.m515unboximpl()});
    }

    public Mat.Tuple3<UShort> getV3c() {
        short[] data = UShortArray.m517constructorimpl(3);
        MatAtKt.m1580getN38XRpM(this.mat, this.indices, data);
        return new Mat.Tuple3<>(UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 0)), UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 1)), UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 2)));
    }

    public void setV3c(Mat.Tuple3<UShort> v) {
        Intrinsics.checkNotNullParameter(v, "v");
        UShort _0 = v.get_0();
        Intrinsics.checkNotNullExpressionValue(_0, "v._0");
        UShort _1 = v.get_1();
        Intrinsics.checkNotNullExpressionValue(_1, "v._1");
        UShort _2 = v.get_2();
        Intrinsics.checkNotNullExpressionValue(_2, "v._2");
        MatAtKt.m1584putN38XRpM(this.mat, this.indices, new short[]{_0.m515unboximpl(), _1.m515unboximpl(), _2.m515unboximpl()});
    }

    public Mat.Tuple4<UShort> getV4c() {
        short[] data = UShortArray.m517constructorimpl(4);
        MatAtKt.m1580getN38XRpM(this.mat, this.indices, data);
        return new Mat.Tuple4<>(UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 0)), UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 1)), UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 2)), UShort.m460boximpl(UShortArray.m523getMh2AYeg(data, 3)));
    }

    public void setV4c(Mat.Tuple4<UShort> v) {
        Intrinsics.checkNotNullParameter(v, "v");
        UShort _0 = v.get_0();
        Intrinsics.checkNotNullExpressionValue(_0, "v._0");
        UShort _1 = v.get_1();
        Intrinsics.checkNotNullExpressionValue(_1, "v._1");
        UShort _2 = v.get_2();
        Intrinsics.checkNotNullExpressionValue(_2, "v._2");
        UShort _3 = v.get_3();
        Intrinsics.checkNotNullExpressionValue(_3, "v._3");
        MatAtKt.m1584putN38XRpM(this.mat, this.indices, new short[]{_0.m515unboximpl(), _1.m515unboximpl(), _2.m515unboximpl(), _3.m515unboximpl()});
    }
}
