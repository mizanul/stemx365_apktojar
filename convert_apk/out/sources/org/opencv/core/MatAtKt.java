package org.opencv.core;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UShort;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.apache.xmlrpc.serializer.ObjectArraySerializer;
import org.opencv.core.Mat;

@Metadata(mo11628d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u001a'\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0005\u001a/\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u00022\u0006\u0010\b\u001a\u0002H\u0002¢\u0006\u0002\u0010\t\u001a7\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000b\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u00022\u0006\u0010\b\u001a\u0002H\u00022\u0006\u0010\f\u001a\u0002H\u0002¢\u0006\u0002\u0010\r\u001a+\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000f\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\b\u001a#\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000f\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\b\u001a\u001e\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0002¢\u0006\u0002\u0010\u0017\u001a\u001e\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0002¢\u0006\u0002\u0010\u0018\u001a\u001e\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0002¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u001a\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0002¢\u0006\u0002\u0010\u0017\u001a\u001e\u0010\u001a\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0002¢\u0006\u0002\u0010\u0018\u001a\u001e\u0010\u001a\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0002¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u001b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0002¢\u0006\u0002\u0010\u0018\u001a\u001e\u0010\u001b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0002¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u001c\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0002¢\u0006\u0002\u0010\u0019\u001a/\u0010\u001d\u001a\u00020\u0012*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b \u0010!\u001a/\u0010\u001d\u001a\u00020\u0012*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\"ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b#\u0010$\u001a'\u0010\u001d\u001a\u00020\u0012*\u00020\u00102\u0006\u0010%\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b&\u0010'\u001a'\u0010\u001d\u001a\u00020\u0012*\u00020\u00102\u0006\u0010%\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\"ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b(\u0010)\u001a/\u0010*\u001a\u00020\u0012*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b+\u0010!\u001a/\u0010*\u001a\u00020\u0012*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\"ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b,\u0010$\u001a'\u0010*\u001a\u00020\u0012*\u00020\u00102\u0006\u0010%\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b-\u0010'\u001a'\u0010*\u001a\u00020\u0012*\u00020\u00102\u0006\u0010%\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\"ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b.\u0010)\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006/"}, mo11629d2 = {"T2", "Lorg/opencv/core/Mat$Tuple2;", "T", "_0", "_1", "(Ljava/lang/Object;Ljava/lang/Object;)Lorg/opencv/core/Mat$Tuple2;", "T3", "Lorg/opencv/core/Mat$Tuple3;", "_2", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lorg/opencv/core/Mat$Tuple3;", "T4", "Lorg/opencv/core/Mat$Tuple4;", "_3", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lorg/opencv/core/Mat$Tuple4;", "at", "Lorg/opencv/core/Mat$Atable;", "Lorg/opencv/core/Mat;", "row", "", "col", "idx", "", "component1", "(Lorg/opencv/core/Mat$Tuple2;)Ljava/lang/Object;", "(Lorg/opencv/core/Mat$Tuple3;)Ljava/lang/Object;", "(Lorg/opencv/core/Mat$Tuple4;)Ljava/lang/Object;", "component2", "component3", "component4", "get", "data", "Lkotlin/UByteArray;", "get-K0TZSog", "(Lorg/opencv/core/Mat;II[B)I", "Lkotlin/UShortArray;", "get-rFce7Xw", "(Lorg/opencv/core/Mat;II[S)I", "indices", "get-7tiRaYo", "(Lorg/opencv/core/Mat;[I[B)I", "get-N38XRpM", "(Lorg/opencv/core/Mat;[I[S)I", "put", "put-K0TZSog", "put-rFce7Xw", "put-7tiRaYo", "put-N38XRpM", "opencv-contrib_release"}, mo11630k = 2, mo11631mv = {1, 5, 1}, mo11633xi = 48)
/* compiled from: MatAt.kt */
public final class MatAtKt {
    /* renamed from: get-K0TZSog  reason: not valid java name */
    public static final int m1579getK0TZSog(Mat $this$get, int row, int col, byte[] data) {
        Intrinsics.checkNotNullParameter($this$get, "$this$get");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$get.get(row, col, data);
    }

    /* renamed from: get-7tiRaYo  reason: not valid java name */
    public static final int m1578get7tiRaYo(Mat $this$get, int[] indices, byte[] data) {
        Intrinsics.checkNotNullParameter($this$get, "$this$get");
        Intrinsics.checkNotNullParameter(indices, "indices");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$get.get(indices, data);
    }

    /* renamed from: put-K0TZSog  reason: not valid java name */
    public static final int m1583putK0TZSog(Mat $this$put, int row, int col, byte[] data) {
        Intrinsics.checkNotNullParameter($this$put, "$this$put");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$put.put(row, col, data);
    }

    /* renamed from: put-7tiRaYo  reason: not valid java name */
    public static final int m1582put7tiRaYo(Mat $this$put, int[] indices, byte[] data) {
        Intrinsics.checkNotNullParameter($this$put, "$this$put");
        Intrinsics.checkNotNullParameter(indices, "indices");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$put.put(indices, data);
    }

    /* renamed from: get-rFce7Xw  reason: not valid java name */
    public static final int m1581getrFce7Xw(Mat $this$get, int row, int col, short[] data) {
        Intrinsics.checkNotNullParameter($this$get, "$this$get");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$get.get(row, col, data);
    }

    /* renamed from: get-N38XRpM  reason: not valid java name */
    public static final int m1580getN38XRpM(Mat $this$get, int[] indices, short[] data) {
        Intrinsics.checkNotNullParameter($this$get, "$this$get");
        Intrinsics.checkNotNullParameter(indices, "indices");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$get.get(indices, data);
    }

    /* renamed from: put-rFce7Xw  reason: not valid java name */
    public static final int m1585putrFce7Xw(Mat $this$put, int row, int col, short[] data) {
        Intrinsics.checkNotNullParameter($this$put, "$this$put");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$put.put(row, col, data);
    }

    /* renamed from: put-N38XRpM  reason: not valid java name */
    public static final int m1584putN38XRpM(Mat $this$put, int[] indices, short[] data) {
        Intrinsics.checkNotNullParameter($this$put, "$this$put");
        Intrinsics.checkNotNullParameter(indices, "indices");
        Intrinsics.checkNotNullParameter(data, ObjectArraySerializer.DATA_TAG);
        return $this$put.put(indices, data);
    }

    /* renamed from: at */
    public static final /* synthetic */ <T> Mat.Atable<T> m172at(Mat $this$at, int row, int col) {
        Intrinsics.checkNotNullParameter($this$at, "<this>");
        Intrinsics.reifiedOperationMarker(4, "T");
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Object.class);
        boolean z = true;
        if (!(Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Byte.TYPE)) ? true : Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Double.TYPE)) ? true : Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Float.TYPE)) ? true : Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Integer.TYPE)))) {
            z = Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Short.TYPE));
        }
        if (z) {
            Intrinsics.reifiedOperationMarker(4, "T");
            Mat.Atable<T> at = $this$at.mo16209at(Object.class, row, col);
            Intrinsics.checkNotNullExpressionValue(at, "this.at(\n            T::class.java,\n            row,\n            col\n        )");
            return at;
        } else if (Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(UByte.class))) {
            return new AtableUByte($this$at, row, col);
        } else {
            if (Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(UShort.class))) {
                return new AtableUShort($this$at, row, col);
            }
            throw new RuntimeException("Unsupported class type");
        }
    }

    /* renamed from: at */
    public static final /* synthetic */ <T> Mat.Atable<T> m173at(Mat $this$at, int[] idx) {
        Intrinsics.checkNotNullParameter($this$at, "<this>");
        Intrinsics.checkNotNullParameter(idx, "idx");
        Intrinsics.reifiedOperationMarker(4, "T");
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Object.class);
        boolean z = true;
        if (!(Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Byte.TYPE)) ? true : Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Double.TYPE)) ? true : Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Float.TYPE)) ? true : Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Integer.TYPE)))) {
            z = Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(Short.TYPE));
        }
        if (z) {
            Intrinsics.reifiedOperationMarker(4, "T");
            Mat.Atable<T> at = $this$at.mo16210at(Object.class, idx);
            Intrinsics.checkNotNullExpressionValue(at, "this.at(\n            T::class.java,\n            idx\n        )");
            return at;
        } else if (Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(UByte.class))) {
            return new AtableUByte($this$at, idx);
        } else {
            if (Intrinsics.areEqual((Object) orCreateKotlinClass, (Object) Reflection.getOrCreateKotlinClass(UShort.class))) {
                return new AtableUShort($this$at, idx);
            }
            throw new RuntimeException("Unsupported class type");
        }
    }

    public static final <T> T component1(Mat.Tuple2<T> $this$component1) {
        Intrinsics.checkNotNullParameter($this$component1, "<this>");
        return $this$component1.get_0();
    }

    public static final <T> T component2(Mat.Tuple2<T> $this$component2) {
        Intrinsics.checkNotNullParameter($this$component2, "<this>");
        return $this$component2.get_1();
    }

    public static final <T> T component1(Mat.Tuple3<T> $this$component1) {
        Intrinsics.checkNotNullParameter($this$component1, "<this>");
        return $this$component1.get_0();
    }

    public static final <T> T component2(Mat.Tuple3<T> $this$component2) {
        Intrinsics.checkNotNullParameter($this$component2, "<this>");
        return $this$component2.get_1();
    }

    public static final <T> T component3(Mat.Tuple3<T> $this$component3) {
        Intrinsics.checkNotNullParameter($this$component3, "<this>");
        return $this$component3.get_2();
    }

    public static final <T> T component1(Mat.Tuple4<T> $this$component1) {
        Intrinsics.checkNotNullParameter($this$component1, "<this>");
        return $this$component1.get_0();
    }

    public static final <T> T component2(Mat.Tuple4<T> $this$component2) {
        Intrinsics.checkNotNullParameter($this$component2, "<this>");
        return $this$component2.get_1();
    }

    public static final <T> T component3(Mat.Tuple4<T> $this$component3) {
        Intrinsics.checkNotNullParameter($this$component3, "<this>");
        return $this$component3.get_2();
    }

    public static final <T> T component4(Mat.Tuple4<T> $this$component4) {
        Intrinsics.checkNotNullParameter($this$component4, "<this>");
        return $this$component4.get_3();
    }

    /* renamed from: T2 */
    public static final <T> Mat.Tuple2<T> m169T2(T _0, T _1) {
        return new Mat.Tuple2<>(_0, _1);
    }

    /* renamed from: T3 */
    public static final <T> Mat.Tuple3<T> m170T3(T _0, T _1, T _2) {
        return new Mat.Tuple3<>(_0, _1, _2);
    }

    /* renamed from: T4 */
    public static final <T> Mat.Tuple4<T> m171T4(T _0, T _1, T _2, T _3) {
        return new Mat.Tuple4<>(_0, _1, _2, _3);
    }
}
