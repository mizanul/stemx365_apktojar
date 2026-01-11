package gov.nasa.arc.astrobee.ros.internal.util;

import ff_msgs.AckStamped;
import ff_msgs.CommandArg;
import ff_msgs.CommandStamped;
import ff_msgs.GuestScienceApk;
import ff_msgs.GuestScienceCommand;
import ff_msgs.GuestScienceConfig;
import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.Result;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.text.Typography;

public final class Stringer {
    private static final String[] s_dataTypes = {"BOOL(0)", "DOUBLE(1)", "FLOAT(2)", "INT(3)", "LONG(4)", "STRING(5)", "VEC3d(6)", "MAT33f(7)"};

    private Stringer() {
    }

    public static String toString(GuestScienceConfig config) {
        if (config == null) {
            return "GuestScienceConfig{null}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("GuestScienceConfig{");
        for (GuestScienceApk apk : config.getApks()) {
            sb.append("Apk{");
            sb.append("apkName=");
            sb.append(apk.getApkName());
            sb.append("; ");
            sb.append("shortName=");
            sb.append(apk.getShortName());
            sb.append("; ");
            sb.append("primary=");
            sb.append(apk.getPrimary());
            sb.append("; ");
            for (GuestScienceCommand cmd : apk.getCommands()) {
                sb.append("GuestScienceCommand{");
                sb.append("name=");
                sb.append(cmd.getName());
                sb.append("; ");
                sb.append("command=");
                sb.append(cmd.getCommand());
                sb.append("} ");
            }
            sb.append("}; ");
        }
        sb.append("}");
        return sb.toString();
    }

    public static String toString(AckStamped ack) {
        if (ack == null) {
            return "Ack{null}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Ack{");
        sb.append("id=");
        sb.append(ack.getCmdId());
        sb.append("; ");
        PendingResult.Status status = null;
        sb.append("status=");
        try {
            status = PendingResult.Status.fromValue(ack.getStatus().getStatus());
            sb.append(status.toString());
        } catch (IllegalArgumentException e) {
            sb.append("UNKNOWN(");
            sb.append(ack.getStatus().getStatus());
            sb.append(")");
        }
        if (status == PendingResult.Status.COMPLETED) {
            sb.append("; ");
            sb.append("completedStatus=");
            try {
                sb.append(Result.Status.fromValue(ack.getCompletedStatus().getStatus()).toString());
            } catch (IllegalArgumentException e2) {
                sb.append("UNKNOWN(");
                sb.append(ack.getCompletedStatus().getStatus());
                sb.append(")");
            }
        }
        String msg = ack.getMessage();
        if (msg != null && msg.length() > 0) {
            sb.append("; ");
            sb.append("message=\"");
            sb.append(msg);
            sb.append(Typography.quote);
        }
        sb.append('}');
        return sb.toString();
    }

    public static String toString(CommandStamped cmd) {
        StringBuilder sb = new StringBuilder();
        sb.append("Command{");
        sb.append("id=");
        sb.append(cmd.getCmdId());
        sb.append("; ");
        sb.append("name=");
        sb.append(cmd.getCmdName());
        sb.append("; ");
        sb.append("src=");
        sb.append(cmd.getCmdSrc());
        sb.append("; ");
        sb.append("origin=");
        sb.append(cmd.getCmdOrigin());
        sb.append("; ");
        String subsystem = cmd.getSubsysName();
        if (subsystem != null && subsystem.length() > 0) {
            sb.append("subsystem=");
            sb.append(cmd.getSubsysName());
            sb.append("; ");
        }
        List<CommandArg> args = cmd.getArgs();
        sb.append("args=[");
        Iterator<CommandArg> it = args.iterator();
        while (it.hasNext()) {
            sb.append(toString(it.next()));
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        sb.append('}');
        return sb.toString();
    }

    private static String toString(CommandArg arg) {
        StringBuilder sb = new StringBuilder();
        sb.append("Arg{");
        byte type = arg.getDataType();
        String[] strArr = s_dataTypes;
        if (type < strArr.length) {
            sb.append(strArr[type]);
        } else {
            sb.append("UNKNOWN(");
            sb.append(type);
            sb.append(")");
        }
        sb.append(": ");
        switch (type) {
            case 0:
                sb.append(arg.getB());
                break;
            case 1:
                sb.append(arg.getD());
                break;
            case 2:
                sb.append(arg.getF());
                break;
            case 3:
                sb.append(arg.getI());
                break;
            case 4:
                sb.append(arg.getLl());
                break;
            case 5:
                sb.append(arg.getS());
                break;
            case 6:
                sb.append(Arrays.toString(arg.getVec3d()));
                break;
            case 7:
                sb.append(Arrays.toString(arg.getMat33f()));
                break;
            default:
                sb.append("UNKNOWN");
                break;
        }
        sb.append('}');
        return sb.toString();
    }
}
