package org.ros.internal.node.topic;

import com.google.common.base.Preconditions;
import java.net.URI;
import org.ros.internal.node.server.NodeIdentifier;
import org.ros.internal.transport.ConnectionHeader;
import org.ros.namespace.GraphName;

public class SubscriberIdentifier {
    private final NodeIdentifier nodeIdentifier;
    private final TopicIdentifier topicIdentifier;

    public SubscriberIdentifier(NodeIdentifier nodeIdentifier2, TopicIdentifier topicIdentifier2) {
        Preconditions.checkNotNull(nodeIdentifier2);
        Preconditions.checkNotNull(topicIdentifier2);
        this.nodeIdentifier = nodeIdentifier2;
        this.topicIdentifier = topicIdentifier2;
    }

    public ConnectionHeader toConnectionHeader() {
        ConnectionHeader connectionHeader = new ConnectionHeader();
        connectionHeader.merge(this.nodeIdentifier.toConnectionHeader());
        connectionHeader.merge(this.topicIdentifier.toConnectionHeader());
        return connectionHeader;
    }

    public NodeIdentifier getNodeIdentifier() {
        return this.nodeIdentifier;
    }

    public URI getUri() {
        return this.nodeIdentifier.getUri();
    }

    public TopicIdentifier getTopicIdentifier() {
        return this.topicIdentifier;
    }

    public GraphName getTopicName() {
        return this.topicIdentifier.getName();
    }

    public String toString() {
        return "SubscriberIdentifier<" + this.nodeIdentifier + ", " + this.topicIdentifier + ">";
    }

    public int hashCode() {
        int i = 1 * 31;
        NodeIdentifier nodeIdentifier2 = this.nodeIdentifier;
        int i2 = 0;
        int result = (i + (nodeIdentifier2 == null ? 0 : nodeIdentifier2.hashCode())) * 31;
        TopicIdentifier topicIdentifier2 = this.topicIdentifier;
        if (topicIdentifier2 != null) {
            i2 = topicIdentifier2.hashCode();
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
        SubscriberIdentifier other = (SubscriberIdentifier) obj;
        NodeIdentifier nodeIdentifier2 = this.nodeIdentifier;
        if (nodeIdentifier2 == null) {
            if (other.nodeIdentifier != null) {
                return false;
            }
        } else if (!nodeIdentifier2.equals(other.nodeIdentifier)) {
            return false;
        }
        TopicIdentifier topicIdentifier2 = this.topicIdentifier;
        if (topicIdentifier2 == null) {
            if (other.topicIdentifier != null) {
                return false;
            }
        } else if (!topicIdentifier2.equals(other.topicIdentifier)) {
            return false;
        }
        return true;
    }
}
