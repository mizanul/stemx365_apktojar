package gov.nasa.arc.astrobee.ros.internal;

import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.internal.CommandBuilder;
import gov.nasa.arc.astrobee.internal.Publishable;
import gov.nasa.arc.astrobee.internal.RobotImpl;

public class DefaultRobot extends RobotImpl {
    private final RobotNodeMain m_nodeMain;

    public DefaultRobot(RobotNodeMain node) {
        this.m_nodeMain = node;
    }

    /* access modifiers changed from: protected */
    public CommandBuilder makeCommandBuilder() {
        return new DefaultCommandBuilder(this.m_nodeMain.getTopicMessageFactory());
    }

    /* access modifiers changed from: protected */
    public PendingResult publish(Publishable cmd) {
        return this.m_nodeMain.publish(((CommandHolder) cmd).getCommand());
    }

    public Kinematics getCurrentKinematics() {
        return this.m_nodeMain.getKinematics();
    }
}
