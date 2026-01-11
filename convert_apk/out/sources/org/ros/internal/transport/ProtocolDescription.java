package org.ros.internal.transport;

import com.google.common.collect.Lists;
import java.net.InetSocketAddress;
import java.util.List;
import org.ros.address.AdvertiseAddress;

public class ProtocolDescription {
    private final AdvertiseAddress address;
    private final String name;

    public ProtocolDescription(String name2, AdvertiseAddress address2) {
        this.name = name2;
        this.address = address2;
    }

    public String getName() {
        return this.name;
    }

    public AdvertiseAddress getAdverstiseAddress() {
        return this.address;
    }

    public InetSocketAddress getAddress() {
        return this.address.toInetSocketAddress();
    }

    public List<Object> toList() {
        return Lists.newArrayList((E[]) new Object[]{this.name, this.address.getHost(), Integer.valueOf(this.address.getPort())});
    }

    public String toString() {
        return "Protocol<" + this.name + ", " + getAdverstiseAddress() + ">";
    }

    public int hashCode() {
        int i = 1 * 31;
        AdvertiseAddress advertiseAddress = this.address;
        int i2 = 0;
        int result = (i + (advertiseAddress == null ? 0 : advertiseAddress.hashCode())) * 31;
        String str = this.name;
        if (str != null) {
            i2 = str.hashCode();
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
        ProtocolDescription other = (ProtocolDescription) obj;
        AdvertiseAddress advertiseAddress = this.address;
        if (advertiseAddress == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!advertiseAddress.equals(other.address)) {
            return false;
        }
        String str = this.name;
        if (str == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!str.equals(other.name)) {
            return false;
        }
        return true;
    }
}
