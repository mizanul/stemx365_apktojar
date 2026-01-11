package org.ros.internal.node.service;

import com.google.common.base.Preconditions;
import java.net.URI;
import org.ros.namespace.GraphName;

public class ServiceIdentifier {
    private final GraphName name;
    private final URI uri;

    public ServiceIdentifier(GraphName name2, URI uri2) {
        Preconditions.checkNotNull(name2);
        Preconditions.checkArgument(name2.isGlobal());
        this.name = name2;
        this.uri = uri2;
    }

    public GraphName getName() {
        return this.name;
    }

    public URI getUri() {
        return this.uri;
    }

    public String toString() {
        return "ServiceIdentifier<" + this.name + ", " + this.uri + ">";
    }

    public int hashCode() {
        int i = 1 * 31;
        GraphName graphName = this.name;
        int i2 = 0;
        int result = (i + (graphName == null ? 0 : graphName.hashCode())) * 31;
        URI uri2 = this.uri;
        if (uri2 != null) {
            i2 = uri2.hashCode();
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
        ServiceIdentifier other = (ServiceIdentifier) obj;
        GraphName graphName = this.name;
        if (graphName == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!graphName.equals(other.name)) {
            return false;
        }
        URI uri2 = this.uri;
        if (uri2 == null) {
            if (other.uri != null) {
                return false;
            }
        } else if (!uri2.equals(other.uri)) {
            return false;
        }
        return true;
    }
}
