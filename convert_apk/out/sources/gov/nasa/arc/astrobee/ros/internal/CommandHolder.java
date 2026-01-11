package gov.nasa.arc.astrobee.ros.internal;

import ff_msgs.CommandStamped;
import gov.nasa.arc.astrobee.internal.Publishable;

final class CommandHolder implements Publishable {
    private final CommandStamped m_command;

    CommandHolder(CommandStamped cmd) {
        this.m_command = cmd;
    }

    /* access modifiers changed from: package-private */
    public CommandStamped getCommand() {
        return this.m_command;
    }
}
