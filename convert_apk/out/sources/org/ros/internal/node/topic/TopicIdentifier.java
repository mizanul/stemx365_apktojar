package org.ros.internal.node.topic;

import com.google.common.base.Preconditions;
import org.ros.internal.transport.ConnectionHeader;
import org.ros.internal.transport.ConnectionHeaderFields;
import org.ros.namespace.GraphName;

public class TopicIdentifier {
    private final GraphName name;

    public static TopicIdentifier forName(String name2) {
        return new TopicIdentifier(GraphName.m181of(name2));
    }

    public TopicIdentifier(GraphName name2) {
        Preconditions.checkNotNull(name2);
        Preconditions.checkArgument(name2.isGlobal());
        this.name = name2;
    }

    public ConnectionHeader toConnectionHeader() {
        ConnectionHeader connectionHeader = new ConnectionHeader();
        connectionHeader.addField(ConnectionHeaderFields.TOPIC, this.name.toString());
        return connectionHeader;
    }

    public GraphName getName() {
        return this.name;
    }

    public String toString() {
        return "TopicIdentifier<" + this.name + ">";
    }

    public int hashCode() {
        int i = 1 * 31;
        GraphName graphName = this.name;
        return i + (graphName == null ? 0 : graphName.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TopicIdentifier other = (TopicIdentifier) obj;
        GraphName graphName = this.name;
        if (graphName == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!graphName.equals(other.name)) {
            return false;
        }
        return true;
    }
}
