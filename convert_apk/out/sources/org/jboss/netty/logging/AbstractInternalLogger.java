package org.jboss.netty.logging;

public abstract class AbstractInternalLogger implements InternalLogger {
    protected AbstractInternalLogger() {
    }

    /* renamed from: org.jboss.netty.logging.AbstractInternalLogger$1 */
    static /* synthetic */ class C09011 {
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$logging$InternalLogLevel;

        static {
            int[] iArr = new int[InternalLogLevel.values().length];
            $SwitchMap$org$jboss$netty$logging$InternalLogLevel = iArr;
            try {
                iArr[InternalLogLevel.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jboss$netty$logging$InternalLogLevel[InternalLogLevel.INFO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jboss$netty$logging$InternalLogLevel[InternalLogLevel.WARN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$jboss$netty$logging$InternalLogLevel[InternalLogLevel.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public boolean isEnabled(InternalLogLevel level) {
        int i = C09011.$SwitchMap$org$jboss$netty$logging$InternalLogLevel[level.ordinal()];
        if (i == 1) {
            return isDebugEnabled();
        }
        if (i == 2) {
            return isInfoEnabled();
        }
        if (i == 3) {
            return isWarnEnabled();
        }
        if (i == 4) {
            return isErrorEnabled();
        }
        throw new Error();
    }

    public void log(InternalLogLevel level, String msg, Throwable cause) {
        int i = C09011.$SwitchMap$org$jboss$netty$logging$InternalLogLevel[level.ordinal()];
        if (i == 1) {
            debug(msg, cause);
        } else if (i == 2) {
            info(msg, cause);
        } else if (i == 3) {
            warn(msg, cause);
        } else if (i == 4) {
            error(msg, cause);
        } else {
            throw new Error();
        }
    }

    public void log(InternalLogLevel level, String msg) {
        int i = C09011.$SwitchMap$org$jboss$netty$logging$InternalLogLevel[level.ordinal()];
        if (i == 1) {
            debug(msg);
        } else if (i == 2) {
            info(msg);
        } else if (i == 3) {
            warn(msg);
        } else if (i == 4) {
            error(msg);
        } else {
            throw new Error();
        }
    }
}
