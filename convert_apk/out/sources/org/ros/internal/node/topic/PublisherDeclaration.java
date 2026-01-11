package org.ros.internal.node.topic;

import com.google.common.base.Preconditions;
import java.net.URI;
import org.ros.internal.node.server.NodeIdentifier;
import org.ros.internal.transport.ConnectionHeader;
import org.ros.namespace.GraphName;

public class PublisherDeclaration {
    private final PublisherIdentifier publisherIdentifier;
    private final TopicDeclaration topicDeclaration;

    public static PublisherDeclaration newFromNodeIdentifier(NodeIdentifier nodeIdentifier, TopicDeclaration topicDeclaration2) {
        Preconditions.checkNotNull(nodeIdentifier);
        Preconditions.checkNotNull(topicDeclaration2);
        return new PublisherDeclaration(new PublisherIdentifier(nodeIdentifier, topicDeclaration2.getIdentifier()), topicDeclaration2);
    }

    public PublisherDeclaration(PublisherIdentifier publisherIdentifier2, TopicDeclaration topicDeclaration2) {
        Preconditions.checkNotNull(publisherIdentifier2);
        Preconditions.checkNotNull(topicDeclaration2);
        Preconditions.checkArgument(publisherIdentifier2.getTopicIdentifier().equals(topicDeclaration2.getIdentifier()));
        this.publisherIdentifier = publisherIdentifier2;
        this.topicDeclaration = topicDeclaration2;
    }

    public ConnectionHeader toConnectionHeader() {
        ConnectionHeader connectionHeader = this.publisherIdentifier.toConnectionHeader();
        connectionHeader.merge(this.topicDeclaration.toConnectionHeader());
        return connectionHeader;
    }

    public NodeIdentifier getSlaveIdentifier() {
        return this.publisherIdentifier.getNodeIdentifier();
    }

    public GraphName getSlaveName() {
        return this.publisherIdentifier.getNodeIdentifier().getName();
    }

    public URI getSlaveUri() {
        return this.publisherIdentifier.getNodeUri();
    }

    public GraphName getTopicName() {
        return this.topicDeclaration.getName();
    }

    public String getTopicMessageType() {
        return this.topicDeclaration.getMessageType();
    }

    public String toString() {
        return "PublisherDefinition<" + this.publisherIdentifier + ", " + this.topicDeclaration + ">";
    }

    public int hashCode() {
        int i = 1 * 31;
        PublisherIdentifier publisherIdentifier2 = this.publisherIdentifier;
        int i2 = 0;
        int result = (i + (publisherIdentifier2 == null ? 0 : publisherIdentifier2.hashCode())) * 31;
        TopicDeclaration topicDeclaration2 = this.topicDeclaration;
        if (topicDeclaration2 != null) {
            i2 = topicDeclaration2.hashCode();
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
        PublisherDeclaration other = (PublisherDeclaration) obj;
        PublisherIdentifier publisherIdentifier2 = this.publisherIdentifier;
        if (publisherIdentifier2 == null) {
            if (other.publisherIdentifier != null) {
                return false;
            }
        } else if (!publisherIdentifier2.equals(other.publisherIdentifier)) {
            return false;
        }
        TopicDeclaration topicDeclaration2 = this.topicDeclaration;
        if (topicDeclaration2 == null) {
            if (other.topicDeclaration != null) {
                return false;
            }
        } else if (!topicDeclaration2.equals(other.topicDeclaration)) {
            return false;
        }
        return true;
    }
}
