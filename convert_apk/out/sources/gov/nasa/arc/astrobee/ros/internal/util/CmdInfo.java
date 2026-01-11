package gov.nasa.arc.astrobee.ros.internal.util;

public class CmdInfo {
    public String mApkName;
    public String mId;
    public String mOrigin;
    public CmdType mType;

    public CmdInfo() {
        resetCmd();
    }

    public String getCmdType() {
        if (this.mType == CmdType.START) {
            return "start";
        }
        if (this.mType == CmdType.CUSTOM) {
            return "custom";
        }
        if (this.mType == CmdType.STOP) {
            return "stop";
        }
        return "none";
    }

    public boolean isCmdEmpty() {
        if (this.mId.length() == 0 && this.mOrigin.length() == 0 && this.mApkName.length() == 0 && this.mType == CmdType.NONE) {
            return true;
        }
        return false;
    }

    public void resetCmd() {
        this.mId = "";
        this.mOrigin = "";
        this.mApkName = "";
        this.mType = CmdType.NONE;
    }

    public void setCmd(String id, String origin, String apkName, CmdType type) {
        this.mId = id;
        this.mOrigin = origin;
        this.mApkName = apkName;
        this.mType = type;
    }
}
