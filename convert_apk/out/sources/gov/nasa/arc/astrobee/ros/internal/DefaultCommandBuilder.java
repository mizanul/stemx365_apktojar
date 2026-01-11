package gov.nasa.arc.astrobee.ros.internal;

import ff_msgs.CommandArg;
import ff_msgs.CommandStamped;
import gov.nasa.arc.astrobee.internal.CommandBuilder;
import gov.nasa.arc.astrobee.internal.Publishable;
import gov.nasa.arc.astrobee.types.Mat33f;
import gov.nasa.arc.astrobee.types.Vec3d;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.ros.message.MessageFactory;
import std_msgs.Header;

class DefaultCommandBuilder implements CommandBuilder {
    private final List<CommandArg> m_args = new ArrayList();
    private final CommandStamped m_cmd;
    private final MessageFactory m_messageFactory;

    DefaultCommandBuilder(MessageFactory messageFactory) {
        this.m_messageFactory = messageFactory;
        CommandStamped commandStamped = (CommandStamped) messageFactory.newFromType(CommandStamped._TYPE);
        this.m_cmd = commandStamped;
        commandStamped.setHeader((Header) this.m_messageFactory.newFromType(Header._TYPE));
        this.m_cmd.setCmdOrigin("guest_science");
    }

    public CommandBuilder setName(String name) {
        this.m_cmd.setCmdName(name);
        return this;
    }

    public CommandBuilder setSubsystem(String subsystem) {
        this.m_cmd.setSubsysName(subsystem);
        return this;
    }

    public CommandBuilder addArgument(String name, int value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 3);
        arg.setI(value);
        this.m_args.add(arg);
        return this;
    }

    public CommandBuilder addArgument(String name, long value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 4);
        arg.setLl(value);
        this.m_args.add(arg);
        return this;
    }

    public CommandBuilder addArgument(String name, float value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 2);
        arg.setF(value);
        this.m_args.add(arg);
        return this;
    }

    public CommandBuilder addArgument(String name, double value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 1);
        arg.setD(value);
        this.m_args.add(arg);
        return this;
    }

    public CommandBuilder addArgument(String name, boolean value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 0);
        arg.setB(value);
        this.m_args.add(arg);
        return this;
    }

    public CommandBuilder addArgument(String name, Vec3d value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 6);
        arg.setVec3d(value.toArray());
        this.m_args.add(arg);
        return this;
    }

    public CommandBuilder addArgument(String name, Mat33f value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 7);
        arg.setMat33f(value.toArray());
        this.m_args.add(arg);
        return this;
    }

    public CommandBuilder addArgument(String name, String value) {
        CommandArg arg = (CommandArg) this.m_messageFactory.newFromType(CommandArg._TYPE);
        arg.setDataType((byte) 5);
        arg.setS(value);
        this.m_args.add(arg);
        return this;
    }

    public <E extends Enum<E>> CommandBuilder addArgument(String name, E value) {
        return addArgument(name, value.toString());
    }

    public Publishable build() {
        this.m_cmd.setCmdId(UUID.randomUUID().toString());
        this.m_cmd.setArgs(this.m_args);
        return new CommandHolder(this.m_cmd);
    }
}
