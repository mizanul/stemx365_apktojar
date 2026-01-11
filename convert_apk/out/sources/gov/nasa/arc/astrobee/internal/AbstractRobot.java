package gov.nasa.arc.astrobee.internal;

import gov.nasa.arc.astrobee.PendingResult;

public abstract class AbstractRobot {
    /* access modifiers changed from: protected */
    public abstract CommandBuilder makeCommandBuilder();

    /* access modifiers changed from: protected */
    public abstract PendingResult publish(Publishable publishable);
}
