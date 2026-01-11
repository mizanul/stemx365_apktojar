package com.google.common.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public final class Predicates {
    /* access modifiers changed from: private */
    public static final Joiner COMMA_JOINER = Joiner.m19on(",");

    private Predicates() {
    }

    public static <T> Predicate<T> alwaysTrue() {
        return ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    public static <T> Predicate<T> alwaysFalse() {
        return ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
    }

    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.NOT_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate(predicate);
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components) {
        return new AndPredicate(defensiveCopy(components));
    }

    public static <T> Predicate<T> and(Predicate<? super T>... components) {
        return new AndPredicate(defensiveCopy((T[]) components));
    }

    public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) {
        return new AndPredicate(asList((Predicate) Preconditions.checkNotNull(first), (Predicate) Preconditions.checkNotNull(second)));
    }

    /* renamed from: or */
    public static <T> Predicate<T> m26or(Iterable<? extends Predicate<? super T>> components) {
        return new OrPredicate(defensiveCopy(components));
    }

    /* renamed from: or */
    public static <T> Predicate<T> m27or(Predicate<? super T>... components) {
        return new OrPredicate(defensiveCopy((T[]) components));
    }

    /* renamed from: or */
    public static <T> Predicate<T> m25or(Predicate<? super T> first, Predicate<? super T> second) {
        return new OrPredicate(asList((Predicate) Preconditions.checkNotNull(first), (Predicate) Preconditions.checkNotNull(second)));
    }

    public static <T> Predicate<T> equalTo(@Nullable T target) {
        return target == null ? isNull() : new IsEqualToPredicate(target);
    }

    public static Predicate<Object> instanceOf(Class<?> clazz) {
        return new InstanceOfPredicate(clazz);
    }

    public static Predicate<Class<?>> assignableFrom(Class<?> clazz) {
        return new AssignableFromPredicate(clazz);
    }

    /* renamed from: in */
    public static <T> Predicate<T> m24in(Collection<? extends T> target) {
        return new InPredicate(target);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate(predicate, function);
    }

    public static Predicate<CharSequence> containsPattern(String pattern) {
        return new ContainsPatternPredicate(pattern);
    }

    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new ContainsPatternPredicate(pattern);
    }

    enum ObjectPredicate implements Predicate<Object> {
        ALWAYS_TRUE {
            public boolean apply(@Nullable Object o) {
                return true;
            }
        },
        ALWAYS_FALSE {
            public boolean apply(@Nullable Object o) {
                return false;
            }
        },
        IS_NULL {
            public boolean apply(@Nullable Object o) {
                return o == null;
            }
        },
        NOT_NULL {
            public boolean apply(@Nullable Object o) {
                return o != null;
            }
        };

        /* access modifiers changed from: package-private */
        public <T> Predicate<T> withNarrowedType() {
            return this;
        }
    }

    private static class NotPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Predicate<T> predicate;

        NotPredicate(Predicate<T> predicate2) {
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate2);
        }

        public boolean apply(T t) {
            return !this.predicate.apply(t);
        }

        public int hashCode() {
            return ~this.predicate.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof NotPredicate) {
                return this.predicate.equals(((NotPredicate) obj).predicate);
            }
            return false;
        }

        public String toString() {
            return "Not(" + this.predicate.toString() + ")";
        }
    }

    private static class AndPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final List<? extends Predicate<? super T>> components;

        private AndPredicate(List<? extends Predicate<? super T>> components2) {
            this.components = components2;
        }

        public boolean apply(T t) {
            for (int i = 0; i < this.components.size(); i++) {
                if (!((Predicate) this.components.get(i)).apply(t)) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return this.components.hashCode() + 306654252;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof AndPredicate) {
                return this.components.equals(((AndPredicate) obj).components);
            }
            return false;
        }

        public String toString() {
            return "And(" + Predicates.COMMA_JOINER.join((Iterable<?>) this.components) + ")";
        }
    }

    private static class OrPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final List<? extends Predicate<? super T>> components;

        private OrPredicate(List<? extends Predicate<? super T>> components2) {
            this.components = components2;
        }

        public boolean apply(T t) {
            for (int i = 0; i < this.components.size(); i++) {
                if (((Predicate) this.components.get(i)).apply(t)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.components.hashCode() + 87855567;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof OrPredicate) {
                return this.components.equals(((OrPredicate) obj).components);
            }
            return false;
        }

        public String toString() {
            return "Or(" + Predicates.COMMA_JOINER.join((Iterable<?>) this.components) + ")";
        }
    }

    private static class IsEqualToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final T target;

        private IsEqualToPredicate(T target2) {
            this.target = target2;
        }

        public boolean apply(T t) {
            return this.target.equals(t);
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof IsEqualToPredicate) {
                return this.target.equals(((IsEqualToPredicate) obj).target);
            }
            return false;
        }

        public String toString() {
            return "IsEqualTo(" + this.target + ")";
        }
    }

    private static class InstanceOfPredicate implements Predicate<Object>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<?> clazz;

        private InstanceOfPredicate(Class<?> clazz2) {
            this.clazz = (Class) Preconditions.checkNotNull(clazz2);
        }

        public boolean apply(@Nullable Object o) {
            return this.clazz.isInstance(o);
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof InstanceOfPredicate) || this.clazz != ((InstanceOfPredicate) obj).clazz) {
                return false;
            }
            return true;
        }

        public String toString() {
            return "IsInstanceOf(" + this.clazz.getName() + ")";
        }
    }

    private static class AssignableFromPredicate implements Predicate<Class<?>>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<?> clazz;

        private AssignableFromPredicate(Class<?> clazz2) {
            this.clazz = (Class) Preconditions.checkNotNull(clazz2);
        }

        public boolean apply(Class<?> input) {
            return this.clazz.isAssignableFrom(input);
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof AssignableFromPredicate) || this.clazz != ((AssignableFromPredicate) obj).clazz) {
                return false;
            }
            return true;
        }

        public String toString() {
            return "IsAssignableFrom(" + this.clazz.getName() + ")";
        }
    }

    private static class InPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Collection<?> target;

        private InPredicate(Collection<?> target2) {
            this.target = (Collection) Preconditions.checkNotNull(target2);
        }

        public boolean apply(T t) {
            try {
                return this.target.contains(t);
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e2) {
                return false;
            }
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof InPredicate) {
                return this.target.equals(((InPredicate) obj).target);
            }
            return false;
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public String toString() {
            return "In(" + this.target + ")";
        }
    }

    private static class CompositionPredicate<A, B> implements Predicate<A>, Serializable {
        private static final long serialVersionUID = 0;

        /* renamed from: f */
        final Function<A, ? extends B> f62f;

        /* renamed from: p */
        final Predicate<B> f63p;

        private CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f) {
            this.f63p = (Predicate) Preconditions.checkNotNull(p);
            this.f62f = (Function) Preconditions.checkNotNull(f);
        }

        public boolean apply(A a) {
            return this.f63p.apply(this.f62f.apply(a));
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof CompositionPredicate)) {
                return false;
            }
            CompositionPredicate<?, ?> that = (CompositionPredicate) obj;
            if (!this.f62f.equals(that.f62f) || !this.f63p.equals(that.f63p)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.f62f.hashCode() ^ this.f63p.hashCode();
        }

        public String toString() {
            return this.f63p.toString() + "(" + this.f62f.toString() + ")";
        }
    }

    private static class ContainsPatternPredicate implements Predicate<CharSequence>, Serializable {
        private static final long serialVersionUID = 0;
        final Pattern pattern;

        ContainsPatternPredicate(Pattern pattern2) {
            this.pattern = (Pattern) Preconditions.checkNotNull(pattern2);
        }

        ContainsPatternPredicate(String patternStr) {
            this(Pattern.compile(patternStr));
        }

        public boolean apply(CharSequence t) {
            return this.pattern.matcher(t).find();
        }

        public int hashCode() {
            return Objects.hashCode(this.pattern.pattern(), Integer.valueOf(this.pattern.flags()));
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof ContainsPatternPredicate)) {
                return false;
            }
            ContainsPatternPredicate that = (ContainsPatternPredicate) obj;
            if (!Objects.equal(this.pattern.pattern(), that.pattern.pattern()) || !Objects.equal(Integer.valueOf(this.pattern.flags()), Integer.valueOf(that.pattern.flags()))) {
                return false;
            }
            return true;
        }

        public String toString() {
            return Objects.toStringHelper((Object) this).add("pattern", (Object) this.pattern).add("pattern.flags", (Object) Integer.toHexString(this.pattern.flags())).toString();
        }
    }

    private static <T> List<Predicate<? super T>> asList(Predicate<? super T> first, Predicate<? super T> second) {
        return Arrays.asList(new Predicate[]{first, second});
    }

    private static <T> List<T> defensiveCopy(T... array) {
        return defensiveCopy(Arrays.asList(array));
    }

    static <T> List<T> defensiveCopy(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<>();
        for (T element : iterable) {
            list.add(Preconditions.checkNotNull(element));
        }
        return list;
    }
}
