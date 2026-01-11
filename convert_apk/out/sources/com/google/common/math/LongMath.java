package com.google.common.math;

import com.google.common.base.Preconditions;
import java.math.RoundingMode;
import kotlin.jvm.internal.LongCompanionObject;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.Videoio;

public final class LongMath {
    static final int[] BIGGEST_BINOMIALS = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, Videoio.CAP_PROP_XI_SENSOR_OUTPUT_CHANNEL_COUNT, 361, 265, 206, 169, Imgproc.COLOR_COLORCVT_MAX, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
    static final int[] BIGGEST_SIMPLE_BINOMIALS = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, 1313, 684, Videoio.CAP_PROP_XI_AEAG_LEVEL, 287, 214, 169, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
    static final long[] FACTORIALS = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    static final long FLOOR_SQRT_MAX_LONG = 3037000499L;
    static final long[] HALF_POWERS_OF_10 = {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
    static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;
    static final long[] POWERS_OF_10 = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};

    public static boolean isPowerOfTwo(long x) {
        boolean z = true;
        boolean z2 = x > 0;
        if (((x - 1) & x) != 0) {
            z = false;
        }
        return z2 & z;
    }

    /* renamed from: com.google.common.math.LongMath$1 */
    static /* synthetic */ class C07221 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            $SwitchMap$java$math$RoundingMode = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public static int log2(long x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", x);
        switch (C07221.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 64 - Long.numberOfLeadingZeros(x - 1);
            case 6:
            case 7:
            case 8:
                int leadingZeros = Long.numberOfLeadingZeros(x);
                int logFloor = 63 - leadingZeros;
                return x <= (MAX_POWER_OF_SQRT2_UNSIGNED >>> leadingZeros) ? logFloor : logFloor + 1;
            default:
                throw new AssertionError("impossible");
        }
        return 63 - Long.numberOfLeadingZeros(x);
    }

    public static int log10(long x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", x);
        if (fitsInInt(x)) {
            return IntMath.log10((int) x, mode);
        }
        int logFloor = log10Floor(x);
        long floorPow = POWERS_OF_10[logFloor];
        switch (C07221.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(x == floorPow);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return x == floorPow ? logFloor : logFloor + 1;
            case 6:
            case 7:
            case 8:
                return x <= HALF_POWERS_OF_10[logFloor] ? logFloor : logFloor + 1;
            default:
                throw new AssertionError();
        }
        return logFloor;
    }

    static int log10Floor(long x) {
        int i = 1;
        while (true) {
            long[] jArr = POWERS_OF_10;
            if (i >= jArr.length) {
                return jArr.length - 1;
            }
            if (x < jArr[i]) {
                return i - 1;
            }
            i++;
        }
    }

    public static long pow(long b, int k) {
        MathPreconditions.checkNonNegative("exponent", k);
        if (-2 <= b && b <= 2) {
            int i = (int) b;
            if (i != -2) {
                if (i != -1) {
                    if (i != 0) {
                        if (i == 1) {
                            return 1;
                        }
                        if (i == 2) {
                            if (k < 64) {
                                return 1 << k;
                            }
                            return 0;
                        }
                    } else if (k == 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else if ((k & 1) == 0) {
                    return 1;
                } else {
                    return -1;
                }
            } else if (k < 64) {
                return (k & 1) == 0 ? 1 << k : -(1 << k);
            } else {
                return 0;
            }
        }
        long accum = 1;
        while (k != 0) {
            if (k == 1) {
                return accum * b;
            }
            accum *= (k & 1) == 0 ? 1 : b;
            b *= b;
            k >>= 1;
        }
        return accum;
    }

    public static long sqrt(long x, RoundingMode mode) {
        MathPreconditions.checkNonNegative("x", x);
        if (fitsInInt(x)) {
            return (long) IntMath.sqrt((int) x, mode);
        }
        long sqrtFloor = sqrtFloor(x);
        boolean z = true;
        switch (C07221.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                if (sqrtFloor * sqrtFloor != x) {
                    z = false;
                }
                MathPreconditions.checkRoundingUnnecessary(z);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return sqrtFloor * sqrtFloor == x ? sqrtFloor : 1 + sqrtFloor;
            case 6:
            case 7:
            case 8:
                long halfSquare = (sqrtFloor * sqrtFloor) + sqrtFloor;
                boolean z2 = halfSquare >= x;
                if (halfSquare >= 0) {
                    z = false;
                }
                return z2 | z ? sqrtFloor : 1 + sqrtFloor;
            default:
                throw new AssertionError();
        }
        return sqrtFloor;
    }

    private static long sqrtFloor(long x) {
        long sqrt0;
        long sqrt02 = (long) Math.sqrt((double) x);
        long sqrt1 = ((x / sqrt02) + sqrt02) >> 1;
        if (sqrt1 == sqrt02) {
            return sqrt02;
        }
        do {
            sqrt0 = sqrt1;
            sqrt1 = ((x / sqrt0) + sqrt0) >> 1;
        } while (sqrt1 < sqrt0);
        return sqrt0;
    }

    public static long divide(long p, long q, RoundingMode mode) {
        boolean increment;
        RoundingMode roundingMode = mode;
        Preconditions.checkNotNull(mode);
        long div = p / q;
        long rem = p - (q * div);
        if (rem == 0) {
            return div;
        }
        int signum = ((int) ((p ^ q) >> 63)) | 1;
        switch (C07221.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(rem == 0);
                break;
            case 2:
                break;
            case 3:
                increment = signum < 0;
                break;
            case 4:
                increment = true;
                break;
            case 5:
                increment = signum > 0;
                break;
            case 6:
            case 7:
            case 8:
                long absRem = Math.abs(rem);
                long cmpRemToHalfDivisor = absRem - (Math.abs(q) - absRem);
                if (cmpRemToHalfDivisor != 0) {
                    increment = cmpRemToHalfDivisor > 0;
                    break;
                } else {
                    increment = ((roundingMode == RoundingMode.HALF_EVEN) & ((div & 1) != 0)) | (roundingMode == RoundingMode.HALF_UP);
                    break;
                }
            default:
                throw new AssertionError();
        }
        increment = false;
        return increment ? ((long) signum) + div : div;
    }

    public static int mod(long x, int m) {
        return (int) mod(x, (long) m);
    }

    public static long mod(long x, long m) {
        if (m > 0) {
            long result = x % m;
            return result >= 0 ? result : result + m;
        }
        throw new ArithmeticException("Modulus " + m + " must be > 0");
    }

    public static long gcd(long a, long b) {
        MathPreconditions.checkNonNegative("a", a);
        MathPreconditions.checkNonNegative("b", b);
        boolean z = true;
        boolean z2 = a == 0;
        if (b != 0) {
            z = false;
        }
        if (z2 || z) {
            return a | b;
        }
        int aTwos = Long.numberOfTrailingZeros(a);
        long a2 = a >> aTwos;
        int bTwos = Long.numberOfTrailingZeros(b);
        long b2 = b >> bTwos;
        while (a2 != b2) {
            if (a2 < b2) {
                long t = b2;
                b2 = a2;
                a2 = t;
            }
            long a3 = a2 - b2;
            a2 = a3 >> Long.numberOfTrailingZeros(a3);
        }
        return a2 << Math.min(aTwos, bTwos);
    }

    public static long checkedAdd(long a, long b) {
        long result = a + b;
        boolean z = true;
        boolean z2 = (a ^ b) < 0;
        if ((a ^ result) < 0) {
            z = false;
        }
        MathPreconditions.checkNoOverflow(z2 | z);
        return result;
    }

    public static long checkedSubtract(long a, long b) {
        long result = a - b;
        boolean z = true;
        boolean z2 = (a ^ b) >= 0;
        if ((a ^ result) < 0) {
            z = false;
        }
        MathPreconditions.checkNoOverflow(z2 | z);
        return result;
    }

    public static long checkedMultiply(long a, long b) {
        int leadingZeros = Long.numberOfLeadingZeros(a) + Long.numberOfLeadingZeros(~a) + Long.numberOfLeadingZeros(b) + Long.numberOfLeadingZeros(~b);
        if (leadingZeros > 65) {
            return a * b;
        }
        boolean z = true;
        MathPreconditions.checkNoOverflow(leadingZeros >= 64);
        MathPreconditions.checkNoOverflow((a >= 0) | (b != Long.MIN_VALUE));
        long result = a * b;
        if (!(a == 0 || result / a == b)) {
            z = false;
        }
        MathPreconditions.checkNoOverflow(z);
        return result;
    }

    public static long checkedPow(long b, int k) {
        MathPreconditions.checkNonNegative("exponent", k);
        boolean z = false;
        if ((b >= -2) && (b <= 2)) {
            int i = (int) b;
            if (i == -2) {
                if (k < 64) {
                    z = true;
                }
                MathPreconditions.checkNoOverflow(z);
                return (k & 1) == 0 ? 1 << k : -1 << k;
            } else if (i != -1) {
                if (i != 0) {
                    if (i == 1) {
                        return 1;
                    }
                    if (i == 2) {
                        if (k < 63) {
                            z = true;
                        }
                        MathPreconditions.checkNoOverflow(z);
                        return 1 << k;
                    }
                } else if (k == 0) {
                    return 1;
                } else {
                    return 0;
                }
            } else if ((k & 1) == 0) {
                return 1;
            } else {
                return -1;
            }
        }
        long accum = 1;
        while (k != 0) {
            if (k == 1) {
                return checkedMultiply(accum, b);
            }
            if ((k & 1) != 0) {
                accum = checkedMultiply(accum, b);
            }
            k >>= 1;
            if (k > 0) {
                MathPreconditions.checkNoOverflow(b <= FLOOR_SQRT_MAX_LONG);
                b *= b;
            }
        }
        return accum;
    }

    public static long factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        long[] jArr = FACTORIALS;
        return n < jArr.length ? jArr[n] : LongCompanionObject.MAX_VALUE;
    }

    public static long binomial(int n, int k) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", k);
        Preconditions.checkArgument(k <= n, "k (%s) > n (%s)", Integer.valueOf(k), Integer.valueOf(n));
        if (k > (n >> 1)) {
            k = n - k;
        }
        int[] iArr = BIGGEST_BINOMIALS;
        if (k >= iArr.length || n > iArr[k]) {
            return LongCompanionObject.MAX_VALUE;
        }
        long result = 1;
        int[] iArr2 = BIGGEST_SIMPLE_BINOMIALS;
        if (k >= iArr2.length || n > iArr2[k]) {
            int i = 1;
            while (i <= k) {
                int d = IntMath.gcd(n, i);
                result = (result / ((long) (i / d))) * ((long) (n / d));
                i++;
                n--;
            }
        } else {
            for (int i2 = 0; i2 < k; i2++) {
                result = (result * ((long) (n - i2))) / ((long) (i2 + 1));
            }
        }
        return result;
    }

    static boolean fitsInInt(long x) {
        return ((long) ((int) x)) == x;
    }

    private LongMath() {
    }
}
