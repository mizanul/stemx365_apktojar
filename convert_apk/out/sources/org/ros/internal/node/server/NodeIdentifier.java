package org.ros.internal.node.server;

import com.google.common.base.Preconditions;
import java.net.URI;
import java.net.URISyntaxException;
import org.ros.exception.RosRuntimeException;
import org.ros.internal.transport.ConnectionHeader;
import org.ros.internal.transport.ConnectionHeaderFields;
import org.ros.namespace.GraphName;

public class NodeIdentifier {
    private final GraphName name;
    private final URI uri;

    public static NodeIdentifier forName(String name2) {
        return new NodeIdentifier(GraphName.m181of(name2), (URI) null);
    }

    public static NodeIdentifier forUri(String uri2) {
        try {
            return new NodeIdentifier((GraphName) null, new URI(uri2));
        } catch (URISyntaxException e) {
            throw new RosRuntimeException((Throwable) e);
        }
    }

    public static NodeIdentifier forNameAndUri(String name2, String uri2) {
        try {
            return new NodeIdentifier(GraphName.m181of(name2), new URI(uri2));
        } catch (URISyntaxException e) {
            throw new RosRuntimeException((Throwable) e);
        }
    }

    public NodeIdentifier(GraphName name2, URI uri2) {
        Preconditions.checkArgument((name2 == null && uri2 == null) ? false : true);
        if (name2 != null) {
            Preconditions.checkArgument(name2.isGlobal());
        }
        this.name = name2;
        this.uri = uri2;
    }

    public GraphName getName() {
        return this.name;
    }

    public URI getUri() {
        return this.uri;
    }

    public ConnectionHeader toConnectionHeader() {
        ConnectionHeader connectionHeader = new ConnectionHeader();
        connectionHeader.addField(ConnectionHeaderFields.CALLER_ID, this.name.toString());
        return connectionHeader;
    }

    public String toString() {
        return "NodeIdentifier<" + this.name + ", " + this.uri + ">";
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
        NodeIdentifier other = (NodeIdentifier) obj;
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
