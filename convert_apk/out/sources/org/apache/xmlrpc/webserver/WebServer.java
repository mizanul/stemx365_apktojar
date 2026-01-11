package org.apache.xmlrpc.webserver;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.util.ThreadPool;
import org.ros.node.topic.Subscriber;

public class WebServer implements Runnable {
    static final String HTTP_11 = "HTTP/1.1";
    protected final List accept;
    private InetAddress address;
    protected final List deny;
    private Thread listener;
    private boolean paranoid;
    private ThreadPool pool;
    private int port;
    protected final XmlRpcStreamServer server;
    protected ServerSocket serverSocket;

    private class AddressMatcher {
        private final int[] pattern;

        AddressMatcher(String pAddress) {
            try {
                this.pattern = new int[4];
                StringTokenizer st = new StringTokenizer(pAddress, ".");
                if (st.countTokens() == 4) {
                    for (int i = 0; i < 4; i++) {
                        String next = st.nextToken();
                        if (Subscriber.TOPIC_MESSAGE_TYPE_WILDCARD.equals(next)) {
                            this.pattern[i] = 256;
                        } else {
                            this.pattern[i] = (byte) Integer.parseInt(next);
                        }
                    }
                    return;
                }
                throw new IllegalArgumentException();
            } catch (Exception e) {
                throw new IllegalArgumentException("\"" + pAddress + "\" does not represent a valid IP address");
            }
        }

        /* access modifiers changed from: package-private */
        public boolean matches(byte[] pAddress) {
            for (int i = 0; i < 4; i++) {
                int[] iArr = this.pattern;
                if (iArr[i] <= 255 && iArr[i] != pAddress[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public XmlRpcStreamServer newXmlRpcStreamServer() {
        return new ConnectionServer();
    }

    public WebServer(int pPort) {
        this(pPort, (InetAddress) null);
    }

    public WebServer(int pPort, InetAddress pAddr) {
        this.accept = new ArrayList();
        this.deny = new ArrayList();
        this.server = newXmlRpcStreamServer();
        this.address = pAddr;
        this.port = pPort;
    }

    /* access modifiers changed from: protected */
    public ServerSocket createServerSocket(int pPort, int backlog, InetAddress addr) throws IOException {
        return new ServerSocket(pPort, backlog, addr);
    }

    private synchronized void setupServerSocket(int backlog) throws IOException {
        int i = 1;
        while (true) {
            try {
                ServerSocket createServerSocket = createServerSocket(this.port, backlog, this.address);
                this.serverSocket = createServerSocket;
                if (createServerSocket.getSoTimeout() <= 0) {
                    this.serverSocket.setSoTimeout(4096);
                }
            } catch (BindException e) {
                if (i != 10) {
                    long waitUntil = System.currentTimeMillis() + 1000;
                    while (true) {
                        long l = waitUntil - System.currentTimeMillis();
                        if (l <= 0) {
                            break;
                        }
                        try {
                            Thread.sleep(l);
                        } catch (InterruptedException e2) {
                        }
                    }
                    i++;
                } else {
                    throw e;
                }
            }
        }
    }

    public void start() throws IOException {
        setupServerSocket(50);
        if (this.listener == null) {
            Thread thread = new Thread(this, "XML-RPC Weblistener");
            this.listener = thread;
            thread.start();
        }
    }

    public void setParanoid(boolean pParanoid) {
        this.paranoid = pParanoid;
    }

    /* access modifiers changed from: protected */
    public boolean isParanoid() {
        return this.paranoid;
    }

    public void acceptClient(String pAddress) {
        this.accept.add(new AddressMatcher(pAddress));
    }

    public void denyClient(String pAddress) {
        this.deny.add(new AddressMatcher(pAddress));
    }

    /* access modifiers changed from: protected */
    public boolean allowConnection(Socket s) {
        if (!this.paranoid) {
            return true;
        }
        int l = this.deny.size();
        byte[] addr = s.getInetAddress().getAddress();
        for (int i = 0; i < l; i++) {
            if (((AddressMatcher) this.deny.get(i)).matches(addr)) {
                return false;
            }
        }
        int l2 = this.accept.size();
        for (int i2 = 0; i2 < l2; i2++) {
            if (((AddressMatcher) this.accept.get(i2)).matches(addr)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public ThreadPool.Task newTask(WebServer pServer, XmlRpcStreamServer pXmlRpcServer, Socket pSocket) throws IOException {
        return new Connection(pServer, pXmlRpcServer, pSocket);
    }

    /* JADX INFO: finally extract failed */
    public void run() {
        Socket socket;
        this.pool = newThreadPool();
        while (this.listener != null) {
            try {
                try {
                    socket = this.serverSocket.accept();
                    try {
                        socket.setTcpNoDelay(true);
                    } catch (SocketException socketOptEx) {
                        log((Throwable) socketOptEx);
                    }
                    if (allowConnection(socket)) {
                        socket.setSoTimeout(30000);
                        if (this.pool.startTask(newTask(this, this.server, socket))) {
                            socket = null;
                        } else {
                            log("Maximum load of " + this.pool.getMaxThreads() + " exceeded, rejecting client");
                        }
                    }
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (Throwable th) {
                        }
                    }
                } catch (InterruptedIOException e) {
                } catch (Throwable t) {
                    log(t);
                }
            } catch (Throwable th2) {
                ServerSocket serverSocket2 = this.serverSocket;
                if (serverSocket2 != null) {
                    try {
                        serverSocket2.close();
                    } catch (IOException e2) {
                        log((Throwable) e2);
                    }
                }
                this.pool.shutdown();
                throw th2;
            }
        }
        ServerSocket serverSocket3 = this.serverSocket;
        if (serverSocket3 != null) {
            try {
                serverSocket3.close();
            } catch (IOException e3) {
                log((Throwable) e3);
            }
        }
        this.pool.shutdown();
        return;
        throw th;
    }

    /* access modifiers changed from: protected */
    public ThreadPool newThreadPool() {
        return new ThreadPool(this.server.getMaxThreads(), "XML-RPC");
    }

    public synchronized void shutdown() {
        if (this.listener != null) {
            Thread l = this.listener;
            this.listener = null;
            l.interrupt();
            if (this.pool != null) {
                this.pool.shutdown();
            }
        }
    }

    public int getPort() {
        return this.serverSocket.getLocalPort();
    }

    public void log(Throwable pError) {
        this.server.getErrorLogger().log(pError.getMessage() == null ? pError.getClass().getName() : pError.getMessage(), pError);
    }

    public void log(String pMessage) {
        this.server.getErrorLogger().log(pMessage);
    }

    public XmlRpcStreamServer getXmlRpcServer() {
        return this.server;
    }
}
