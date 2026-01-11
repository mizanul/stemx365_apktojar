package org.ros.message;

import com.google.common.base.Preconditions;

public class MessageIdentifier {
    private String name;
    private String pkg;
    private String type;

    /* renamed from: of */
    public static MessageIdentifier m180of(String pkg2, String name2) {
        Preconditions.checkNotNull(pkg2);
        Preconditions.checkNotNull(name2);
        return new MessageIdentifier(pkg2, name2);
    }

    /* renamed from: of */
    public static MessageIdentifier m179of(String type2) {
        Preconditions.checkNotNull(type2);
        if (type2.contains("/")) {
            return new MessageIdentifier(type2);
        }
        throw new IllegalArgumentException(String.format("Type name is invalid or not fully qualified: \"%s\"", new Object[]{type2}));
    }

    private MessageIdentifier(String type2) {
        this.type = type2;
    }

    private MessageIdentifier(String pkg2, String name2) {
        this.pkg = pkg2;
        this.name = name2;
    }

    public String getType() {
        if (this.type == null) {
            StringBuilder stringBuilder = new StringBuilder(this.pkg.length() + this.name.length() + 1);
            stringBuilder.append(this.pkg);
            stringBuilder.append("/");
            stringBuilder.append(this.name);
            this.type = stringBuilder.toString();
        }
        return this.type;
    }

    private void splitType() {
        String[] packageAndName = this.type.split("/", 2);
        this.pkg = packageAndName[0];
        this.name = packageAndName[1];
    }

    public String getPackage() {
        if (this.pkg == null) {
            splitType();
        }
        return this.pkg;
    }

    public String getName() {
        if (this.name == null) {
            splitType();
        }
        return this.name;
    }

    public String toString() {
        return String.format("MessageIdentifier<%s>", new Object[]{this.type});
    }

    public int hashCode() {
        int i = 1 * 31;
        String str = this.type;
        return i + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MessageIdentifier other = (MessageIdentifier) obj;
        String str = this.type;
        if (str == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!str.equals(other.type)) {
            return false;
        }
        return true;
    }
}
