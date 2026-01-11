package org.ros.internal.node.service;

import com.google.common.base.Preconditions;
import java.net.URI;
import org.ros.internal.message.service.ServiceDescription;
import org.ros.internal.transport.ConnectionHeader;
import org.ros.internal.transport.ConnectionHeaderFields;
import org.ros.namespace.GraphName;

public class ServiceDeclaration {
    private final ServiceDescription description;
    private final ServiceIdentifier identifier;

    public ServiceDeclaration(ServiceIdentifier identifier2, ServiceDescription description2) {
        Preconditions.checkNotNull(identifier2);
        Preconditions.checkNotNull(description2);
        this.identifier = identifier2;
        this.description = description2;
    }

    public ConnectionHeader toConnectionHeader() {
        ConnectionHeader connectionHeader = new ConnectionHeader();
        connectionHeader.addField("service", getName().toString());
        connectionHeader.addField(ConnectionHeaderFields.TYPE, this.description.getType());
        connectionHeader.addField(ConnectionHeaderFields.MESSAGE_DEFINITION, this.description.getDefinition());
        connectionHeader.addField(ConnectionHeaderFields.MD5_CHECKSUM, this.description.getMd5Checksum());
        return connectionHeader;
    }

    public String getType() {
        return this.description.getType();
    }

    public String getDefinition() {
        return this.description.getDefinition();
    }

    public GraphName getName() {
        return this.identifier.getName();
    }

    public String toString() {
        return "ServiceDeclaration<" + getName().toString() + ", " + this.description.toString() + ">";
    }

    public String getMd5Checksum() {
        return this.description.getMd5Checksum();
    }

    public URI getUri() {
        return this.identifier.getUri();
    }

    public int hashCode() {
        int i = 1 * 31;
        ServiceIdentifier serviceIdentifier = this.identifier;
        int i2 = 0;
        int result = (i + (serviceIdentifier == null ? 0 : serviceIdentifier.hashCode())) * 31;
        ServiceDescription serviceDescription = this.description;
        if (serviceDescription != null) {
            i2 = serviceDescription.hashCode();
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
        ServiceDeclaration other = (ServiceDeclaration) obj;
        ServiceIdentifier serviceIdentifier = this.identifier;
        if (serviceIdentifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!serviceIdentifier.equals(other.identifier)) {
            return false;
        }
        ServiceDescription serviceDescription = this.description;
        if (serviceDescription == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!serviceDescription.equals(other.description)) {
            return false;
        }
        return true;
    }
}
