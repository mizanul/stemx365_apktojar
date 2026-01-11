package org.ros.internal.message.topic;

import org.ros.message.MessageDeclaration;
import org.ros.message.MessageIdentifier;

public class TopicDescription extends MessageDeclaration {
    private final String md5Checksum;

    public TopicDescription(String type, String definition, String md5Checksum2) {
        super(MessageIdentifier.m179of(type), definition);
        this.md5Checksum = md5Checksum2;
    }

    public String getMd5Checksum() {
        return this.md5Checksum;
    }

    public String toString() {
        return "TopicDescription<" + getType() + ", " + this.md5Checksum + ">";
    }

    public int hashCode() {
        int hashCode = super.hashCode() * 31;
        String str = this.md5Checksum;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        TopicDescription other = (TopicDescription) obj;
        String str = this.md5Checksum;
        if (str == null) {
            if (other.md5Checksum != null) {
                return false;
            }
        } else if (!str.equals(other.md5Checksum)) {
            return false;
        }
        return true;
    }
}
