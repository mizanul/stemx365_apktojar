package com.google.common.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import javax.annotation.Nullable;

public final class Enums {
    private Enums() {
    }

    public static Field getField(Enum<?> enumValue) {
        try {
            return enumValue.getDeclaringClass().getDeclaredField(enumValue.name());
        } catch (NoSuchFieldException impossible) {
            throw new AssertionError(impossible);
        }
    }

    public static <T extends Enum<T>> Function<String, T> valueOfFunction(Class<T> enumClass) {
        return new ValueOfFunction(enumClass);
    }

    private static final class ValueOfFunction<T extends Enum<T>> implements Function<String, T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<T> enumClass;

        private ValueOfFunction(Class<T> enumClass2) {
            this.enumClass = (Class) Preconditions.checkNotNull(enumClass2);
        }

        public T apply(String value) {
            try {
                return Enum.valueOf(this.enumClass, value);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        public boolean equals(@Nullable Object obj) {
            return (obj instanceof ValueOfFunction) && this.enumClass.equals(((ValueOfFunction) obj).enumClass);
        }

        public int hashCode() {
            return this.enumClass.hashCode();
        }

        public String toString() {
            return "Enums.valueOf(" + this.enumClass + ")";
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumClass, String value) {
        Preconditions.checkNotNull(enumClass);
        Preconditions.checkNotNull(value);
        try {
            return Optional.m20of(Enum.valueOf(enumClass, value));
        } catch (IllegalArgumentException e) {
            return Optional.absent();
        }
    }
}
