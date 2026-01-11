package kotlin.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;

@Target({ElementType.METHOD})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY})
@Metadata(mo11628d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, mo11629d2 = {"Lkotlin/internal/HidesMembers;", "", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* compiled from: Annotations.kt */
public @interface HidesMembers {
}
