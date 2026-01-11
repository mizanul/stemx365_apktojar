package org.tensorflow.lite.support.common.internal;

public final class SupportPreconditions {
    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException("The object reference is null.");
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public static String checkNotEmpty(String string) {
        if (string != null && string.length() != 0) {
            return string;
        }
        throw new IllegalArgumentException("Given String is empty or null.");
    }

    public static String checkNotEmpty(String string, Object errorMessage) {
        if (string != null && string.length() != 0) {
            return string;
        }
        throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static int checkElementIndex(int index, int size) {
        return checkElementIndex(index, size, "index");
    }

    public static int checkElementIndex(int index, int size, String desc) {
        if (index >= 0 && index < size) {
            return index;
        }
        throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    private static String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return String.format("%s (%s) must not be negative", new Object[]{desc, Integer.valueOf(index)});
        } else if (size >= 0) {
            return String.format("%s (%s) must be less than size (%s)", new Object[]{desc, Integer.valueOf(index), Integer.valueOf(size)});
        } else {
            throw new IllegalArgumentException("negative size: " + size);
        }
    }

    private SupportPreconditions() {
        throw new AssertionError("SupportPreconditions is Uninstantiable.");
    }
}
