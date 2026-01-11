package gov.nasa.arc.astrobee.ros;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ros.EnvironmentVariables;
import org.ros.address.BindAddress;
import org.ros.address.InetAddressFactory;
import org.ros.exception.RosRuntimeException;
import org.ros.namespace.GraphName;
import org.ros.namespace.NameResolver;
import org.ros.node.NodeConfiguration;

public class RobotConfiguration {
    private final Map<String, String> m_environment;
    private String m_host;
    private URI m_masterUri;
    private String m_nodeName;
    private final Map<GraphName, GraphName> m_remappings;
    private String m_robotName;
    private int m_rpcPort;
    private int m_tcpPort;

    public RobotConfiguration() {
        this(System.getenv());
    }

    public RobotConfiguration(Map<String, String> environment) {
        this.m_masterUri = null;
        this.m_host = null;
        this.m_nodeName = null;
        this.m_robotName = null;
        this.m_tcpPort = 0;
        this.m_rpcPort = 0;
        this.m_environment = environment;
        this.m_remappings = new HashMap();
    }

    public NodeConfiguration build() {
        NodeConfiguration config = NodeConfiguration.newPublic(getHost());
        config.setMasterUri(getMasterUri());
        config.setParentResolver(buildParentResolver());
        config.setRosRoot((File) null);
        config.setRosPackagePath(getRosPackagePath());
        String str = this.m_nodeName;
        if (str != null) {
            config.setNodeName(str);
        }
        int i = this.m_tcpPort;
        if (i > 0) {
            config.setTcpRosBindAddress(BindAddress.newPublic(i));
        }
        int i2 = this.m_rpcPort;
        if (i2 > 0) {
            config.setXmlRpcBindAddress(BindAddress.newPublic(i2));
        }
        return config;
    }

    private String getHost() {
        String str = this.m_host;
        if (str != null) {
            return str;
        }
        if (this.m_environment.containsKey(EnvironmentVariables.ROS_IP)) {
            this.m_host = this.m_environment.get(EnvironmentVariables.ROS_IP);
        } else if (this.m_environment.containsKey(EnvironmentVariables.ROS_HOSTNAME)) {
            this.m_host = this.m_environment.get(EnvironmentVariables.ROS_HOSTNAME);
        } else {
            this.m_host = InetAddressFactory.newLoopback().getHostAddress();
        }
        return this.m_host;
    }

    private URI getMasterUri() {
        URI uri = this.m_masterUri;
        if (uri != null) {
            return uri;
        }
        try {
            if (this.m_environment.containsKey(EnvironmentVariables.ROS_MASTER_URI)) {
                this.m_masterUri = new URI(this.m_environment.get(EnvironmentVariables.ROS_MASTER_URI));
            } else {
                this.m_masterUri = NodeConfiguration.DEFAULT_MASTER_URI;
            }
            return this.m_masterUri;
        } catch (URISyntaxException e) {
            throw new RosRuntimeException("Invalid master URI: " + this.m_masterUri);
        }
    }

    private NameResolver buildParentResolver() {
        GraphName namespace = GraphName.root();
        if (this.m_environment.containsKey(EnvironmentVariables.ROS_NAMESPACE)) {
            namespace = GraphName.m181of(this.m_environment.get(EnvironmentVariables.ROS_NAMESPACE)).toGlobal();
        }
        return new NameResolver(namespace, this.m_remappings);
    }

    private List<File> getRosPackagePath() {
        if (!this.m_environment.containsKey(EnvironmentVariables.ROS_PACKAGE_PATH)) {
            return new ArrayList();
        }
        List<File> paths = new ArrayList<>();
        for (String path : this.m_environment.get(EnvironmentVariables.ROS_PACKAGE_PATH).split(File.pathSeparator)) {
            paths.add(new File(path));
        }
        return paths;
    }

    public String getRobotName() {
        String str = this.m_robotName;
        if (str != null) {
            return str;
        }
        if (this.m_environment.containsKey(EnvironmentVariables.ASTROBEE_ROBOT)) {
            this.m_robotName = this.m_environment.get(EnvironmentVariables.ASTROBEE_ROBOT);
        }
        return this.m_robotName;
    }

    public RobotConfiguration setRobotName(String name) {
        this.m_robotName = name;
        return this;
    }

    public RobotConfiguration setHostname(String hostname) {
        this.m_host = hostname;
        return this;
    }

    public RobotConfiguration setTcpPort(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("port must be between 0 and 65535");
        }
        this.m_tcpPort = port;
        return this;
    }

    public RobotConfiguration setRpcPort(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("port must be between 0 and 65535");
        }
        this.m_rpcPort = port;
        return this;
    }

    public RobotConfiguration setMasterUri(URI uri) {
        this.m_masterUri = uri;
        return this;
    }

    public RobotConfiguration setNodeName(String name) {
        this.m_nodeName = name;
        return this;
    }
}
