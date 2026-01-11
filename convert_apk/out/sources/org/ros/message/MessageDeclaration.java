package org.ros.message;

import com.google.common.base.Preconditions;

public class MessageDeclaration {
    private final String definition;
    private final MessageIdentifier messageIdentifier;

    /* renamed from: of */
    public static MessageDeclaration m178of(String type, String definition2) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(definition2);
        return new MessageDeclaration(MessageIdentifier.m179of(type), definition2);
    }

    public MessageDeclaration(MessageIdentifier messageIdentifier2, String definition2) {
        Preconditions.checkNotNull(messageIdentifier2);
        Preconditions.checkNotNull(definition2);
        this.messageIdentifier = messageIdentifier2;
        this.definition = definition2;
    }

    public MessageIdentifier getMessageIdentifier() {
        return this.messageIdentifier;
    }

    public String getType() {
        return this.messageIdentifier.getType();
    }

    public String getPackage() {
        return this.messageIdentifier.getPackage();
    }

    public String getName() {
        return this.messageIdentifier.getName();
    }

    public String getDefinition() {
        Preconditions.checkNotNull(this.definition);
        return this.definition;
    }

    public String toString() {
        return String.format("MessageDeclaration<%s>", new Object[]{this.messageIdentifier.toString()});
    }

    public int hashCode() {
        int i = 1 * 31;
        String str = this.definition;
        int i2 = 0;
        int result = (i + (str == null ? 0 : str.hashCode())) * 31;
        MessageIdentifier messageIdentifier2 = this.messageIdentifier;
        if (messageIdentifier2 != null) {
            i2 = messageIdentifier2.hashCode();
        }
        return result + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MessageDeclaration other = (MessageDeclaration) obj;
        String str = this.definition;
        if (str == null) {
            if (other.definition != null) {
                return false;
            }
        } else if (!str.equals(other.definition)) {
            return false;
        }
        MessageIdentifier messageIdentifier2 = this.messageIdentifier;
        if (messageIdentifier2 == null) {
            if (other.messageIdentifier != null) {
                return false;
            }
        } else if (!messageIdentifier2.equals(other.messageIdentifier)) {
            return false;
        }
        return true;
    }
}
