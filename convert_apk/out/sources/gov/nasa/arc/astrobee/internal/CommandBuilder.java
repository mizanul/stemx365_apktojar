package gov.nasa.arc.astrobee.internal;

import gov.nasa.arc.astrobee.types.Mat33f;
import gov.nasa.arc.astrobee.types.Vec3d;

public interface CommandBuilder {
    CommandBuilder addArgument(String str, double d);

    CommandBuilder addArgument(String str, float f);

    CommandBuilder addArgument(String str, int i);

    CommandBuilder addArgument(String str, long j);

    CommandBuilder addArgument(String str, Mat33f mat33f);

    CommandBuilder addArgument(String str, Vec3d vec3d);

    <E extends Enum<E>> CommandBuilder addArgument(String str, E e);

    CommandBuilder addArgument(String str, String str2);

    CommandBuilder addArgument(String str, boolean z);

    Publishable build();

    CommandBuilder setName(String str);

    CommandBuilder setSubsystem(String str);
}
