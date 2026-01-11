package org.jboss.netty.channel.socket.nio;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Map;
import java.util.Set;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.internal.SystemPropertyUtil;

final class NioProviderMetadata {
    static final int CONSTRAINT_LEVEL;
    private static final String CONSTRAINT_LEVEL_PROPERTY = "org.jboss.netty.channel.socket.nio.constraintLevel";
    private static final String OLD_CONSTRAINT_LEVEL_PROPERTY = "java.nio.channels.spi.constraintLevel";
    static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) NioProviderMetadata.class);

    static {
        int constraintLevel = SystemPropertyUtil.get(CONSTRAINT_LEVEL_PROPERTY, -1);
        if (constraintLevel < 0 || constraintLevel > 2) {
            constraintLevel = SystemPropertyUtil.get(OLD_CONSTRAINT_LEVEL_PROPERTY, -1);
            if (constraintLevel < 0 || constraintLevel > 2) {
                constraintLevel = -1;
            } else {
                logger.warn("System property 'java.nio.channels.spi.constraintLevel' has been deprecated.  Use 'org.jboss.netty.channel.socket.nio.constraintLevel' instead.");
            }
        }
        if (constraintLevel >= 0) {
            InternalLogger internalLogger = logger;
            internalLogger.debug("Setting the NIO constraint level to: " + constraintLevel);
        }
        if (constraintLevel < 0) {
            constraintLevel = detectConstraintLevelFromSystemProperties();
            if (constraintLevel < 0) {
                constraintLevel = 2;
                if (logger.isDebugEnabled()) {
                    logger.debug("Couldn't determine the NIO constraint level from the system properties; using the safest level (2)");
                }
            } else if (constraintLevel != 0) {
                if (logger.isInfoEnabled()) {
                    InternalLogger internalLogger2 = logger;
                    internalLogger2.info("Using the autodetected NIO constraint level: " + constraintLevel + " (Use better NIO provider for better performance)");
                }
            } else if (logger.isDebugEnabled()) {
                InternalLogger internalLogger3 = logger;
                internalLogger3.debug("Using the autodetected NIO constraint level: " + constraintLevel);
            }
        }
        CONSTRAINT_LEVEL = constraintLevel;
        if (constraintLevel < 0 || constraintLevel > 2) {
            throw new Error("Unexpected NIO constraint level: " + CONSTRAINT_LEVEL + ", please report this error.");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:89:0x0161 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int detectConstraintLevelFromSystemProperties() {
        /*
            java.lang.String r0 = "java.specification.version"
            java.lang.String r1 = org.jboss.netty.util.internal.SystemPropertyUtil.get(r0)
            java.lang.String r0 = "java.vm.info"
            java.lang.String r2 = ""
            java.lang.String r2 = org.jboss.netty.util.internal.SystemPropertyUtil.get((java.lang.String) r0, (java.lang.String) r2)
            java.lang.String r0 = "os.name"
            java.lang.String r3 = org.jboss.netty.util.internal.SystemPropertyUtil.get(r0)
            java.lang.String r0 = "java.vm.vendor"
            java.lang.String r4 = org.jboss.netty.util.internal.SystemPropertyUtil.get(r0)
            r5 = 0
            java.nio.channels.spi.SelectorProvider r0 = java.nio.channels.spi.SelectorProvider.provider()     // Catch:{ Exception -> 0x0028 }
            java.lang.Class r0 = r0.getClass()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r0 = r0.getName()     // Catch:{ Exception -> 0x0028 }
            goto L_0x002b
        L_0x0028:
            r0 = move-exception
            r5 = 0
            r0 = r5
        L_0x002b:
            r5 = -1
            if (r1 == 0) goto L_0x0162
            if (r3 == 0) goto L_0x0162
            if (r4 == 0) goto L_0x0162
            if (r0 != 0) goto L_0x0036
            goto L_0x0162
        L_0x0036:
            java.lang.String r3 = r3.toLowerCase()
            java.lang.String r4 = r4.toLowerCase()
            java.lang.String r6 = "sun"
            int r7 = r4.indexOf(r6)
            java.lang.String r8 = "sun.nio.ch.WindowsSelectorProvider"
            java.lang.String r9 = "windows"
            java.lang.String r10 = "sun.nio.ch.EPollSelectorProvider"
            java.lang.String r11 = "linux"
            java.lang.String r12 = "sun.nio.ch.PollSelectorProvider"
            r13 = 0
            if (r7 < 0) goto L_0x008e
            int r7 = r3.indexOf(r11)
            if (r7 < 0) goto L_0x0069
            boolean r6 = r0.equals(r10)
            if (r6 != 0) goto L_0x0068
            boolean r6 = r0.equals(r12)
            if (r6 == 0) goto L_0x0161
        L_0x0068:
            return r13
        L_0x0069:
            int r7 = r3.indexOf(r9)
            if (r7 < 0) goto L_0x0076
            boolean r6 = r0.equals(r8)
            if (r6 == 0) goto L_0x0161
            return r13
        L_0x0076:
            int r6 = r3.indexOf(r6)
            if (r6 >= 0) goto L_0x0084
            java.lang.String r6 = "solaris"
            int r6 = r3.indexOf(r6)
            if (r6 < 0) goto L_0x0161
        L_0x0084:
            java.lang.String r6 = "sun.nio.ch.DevPollSelectorProvider"
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x0161
            return r13
        L_0x008e:
            java.lang.String r6 = "apple"
            int r6 = r4.indexOf(r6)
            if (r6 < 0) goto L_0x00b0
            java.lang.String r6 = "mac"
            int r6 = r3.indexOf(r6)
            if (r6 < 0) goto L_0x0161
            java.lang.String r6 = "os"
            int r6 = r3.indexOf(r6)
            if (r6 < 0) goto L_0x0161
            java.lang.String r6 = "sun.nio.ch.KQueueSelectorProvider"
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x0161
            return r13
        L_0x00b0:
            java.lang.String r6 = "ibm"
            int r6 = r4.indexOf(r6)
            r7 = 1
            if (r6 < 0) goto L_0x011f
            int r6 = r3.indexOf(r11)
            if (r6 >= 0) goto L_0x00c7
            java.lang.String r6 = "aix"
            int r6 = r3.indexOf(r6)
            if (r6 < 0) goto L_0x0161
        L_0x00c7:
            java.lang.String r6 = "1.5"
            boolean r6 = r1.equals(r6)
            if (r6 != 0) goto L_0x0118
            java.lang.String r6 = "^1\\.5\\D.*$"
            boolean r6 = r1.matches(r6)
            if (r6 == 0) goto L_0x00d8
            goto L_0x0118
        L_0x00d8:
            java.lang.String r6 = "1.6"
            boolean r6 = r1.equals(r6)
            if (r6 != 0) goto L_0x00e8
            java.lang.String r6 = "^1\\.6\\D.*$"
            boolean r6 = r1.matches(r6)
            if (r6 == 0) goto L_0x0161
        L_0x00e8:
            java.lang.String r6 = "(?:^|[^0-9])([2-9][0-9]{3}(?:0[1-9]|1[0-2])(?:0[1-9]|[12][0-9]|3[01]))(?:$|[^0-9])"
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)
            java.util.regex.Matcher r8 = r6.matcher(r2)
            boolean r9 = r8.find()
            if (r9 == 0) goto L_0x0117
            java.lang.String r9 = r8.group(r7)
            long r14 = java.lang.Long.parseLong(r9)
            r16 = 20081105(0x13269d1, double:9.921384E-317)
            int r9 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r9 >= 0) goto L_0x0109
            r5 = 2
            return r5
        L_0x0109:
            boolean r9 = r0.equals(r10)
            if (r9 == 0) goto L_0x0110
            return r13
        L_0x0110:
            boolean r9 = r0.equals(r12)
            if (r9 == 0) goto L_0x0117
            return r7
        L_0x0117:
            goto L_0x0161
        L_0x0118:
            boolean r6 = r0.equals(r12)
            if (r6 == 0) goto L_0x0161
            return r7
        L_0x011f:
            java.lang.String r6 = "bea"
            int r6 = r4.indexOf(r6)
            if (r6 >= 0) goto L_0x0141
            java.lang.String r6 = "oracle"
            int r6 = r4.indexOf(r6)
            if (r6 < 0) goto L_0x0130
            goto L_0x0141
        L_0x0130:
            java.lang.String r6 = "apache"
            int r6 = r4.indexOf(r6)
            if (r6 < 0) goto L_0x0161
            java.lang.String r6 = "org.apache.harmony.nio.internal.SelectorProviderImpl"
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x0161
            return r7
        L_0x0141:
            int r6 = r3.indexOf(r11)
            if (r6 < 0) goto L_0x0154
            boolean r6 = r0.equals(r10)
            if (r6 != 0) goto L_0x0153
            boolean r6 = r0.equals(r12)
            if (r6 == 0) goto L_0x0161
        L_0x0153:
            return r13
        L_0x0154:
            int r6 = r3.indexOf(r9)
            if (r6 < 0) goto L_0x0161
            boolean r6 = r0.equals(r8)
            if (r6 == 0) goto L_0x0161
            return r13
        L_0x0161:
            return r5
        L_0x0162:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.socket.nio.NioProviderMetadata.detectConstraintLevelFromSystemProperties():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0142, code lost:
        r3.done = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:?, code lost:
        r1.shutdownNow();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x019f, code lost:
        if (0 == 0) goto L_0x01d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x01a1, code lost:
        null.done = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:?, code lost:
        r1.shutdownNow();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x01fd, code lost:
        if (0 == 0) goto L_0x022e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x01ff, code lost:
        null.done = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:?, code lost:
        r1.shutdownNow();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int autodetect() {
        /*
            java.util.concurrent.ExecutorService r1 = java.util.concurrent.Executors.newCachedThreadPool()
            r2 = 0
            r3 = 0
            r5 = 1
            r7 = 0
            r8 = 1
            java.nio.channels.ServerSocketChannel r0 = java.nio.channels.ServerSocketChannel.open()     // Catch:{ all -> 0x0231 }
            r2 = r0
            java.net.ServerSocket r0 = r2.socket()     // Catch:{ all -> 0x01d3 }
            java.net.InetSocketAddress r9 = new java.net.InetSocketAddress     // Catch:{ all -> 0x01d3 }
            r9.<init>(r7)     // Catch:{ all -> 0x01d3 }
            r0.bind(r9)     // Catch:{ all -> 0x01d3 }
            r2.configureBlocking(r7)     // Catch:{ all -> 0x01d3 }
            org.jboss.netty.channel.socket.nio.NioProviderMetadata$SelectorLoop r0 = new org.jboss.netty.channel.socket.nio.NioProviderMetadata$SelectorLoop     // Catch:{ all -> 0x0175 }
            r0.<init>()     // Catch:{ all -> 0x0175 }
            r3 = r0
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x0118 }
            r2.register(r0, r7)     // Catch:{ all -> 0x0118 }
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x0231 }
            java.nio.channels.SelectionKey r0 = r2.keyFor(r0)     // Catch:{ all -> 0x0231 }
            r9 = r0
            r1.execute(r3)     // Catch:{ all -> 0x0231 }
            r10 = 1
            r0 = 0
            r11 = r0
        L_0x0039:
            r12 = 500000000(0x1dcd6500, double:2.47032823E-315)
            r14 = 50
            r4 = 10
            if (r11 >= r4) goto L_0x007c
        L_0x0042:
            boolean r0 = r3.selecting     // Catch:{ all -> 0x0078 }
            if (r0 != 0) goto L_0x004a
            java.lang.Thread.yield()     // Catch:{ all -> 0x0078 }
            goto L_0x0042
        L_0x004a:
            java.lang.Thread.sleep(r14)     // Catch:{ InterruptedException -> 0x004e }
            goto L_0x004f
        L_0x004e:
            r0 = move-exception
        L_0x004f:
            boolean r0 = r3.selecting     // Catch:{ all -> 0x0078 }
            if (r0 == 0) goto L_0x0042
            long r16 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0078 }
            int r0 = r9.interestOps()     // Catch:{ all -> 0x0078 }
            r0 = r0 | 16
            r9.interestOps(r0)     // Catch:{ all -> 0x0078 }
            int r0 = r9.interestOps()     // Catch:{ all -> 0x0078 }
            r0 = r0 & -17
            r9.interestOps(r0)     // Catch:{ all -> 0x0078 }
            long r18 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0078 }
            long r18 = r18 - r16
            int r0 = (r18 > r12 ? 1 : (r18 == r12 ? 0 : -1))
            if (r0 < 0) goto L_0x0075
            r10 = 0
            goto L_0x007c
        L_0x0075:
            int r11 = r11 + 1
            goto L_0x0039
        L_0x0078:
            r0 = move-exception
            r4 = r7
            goto L_0x0234
        L_0x007c:
            if (r10 == 0) goto L_0x0081
            r0 = 0
            r4 = r0
            goto L_0x00cc
        L_0x0081:
            r10 = 1
            r0 = 0
            r11 = r0
        L_0x0084:
            if (r11 >= r4) goto L_0x00c5
        L_0x0086:
            boolean r0 = r3.selecting     // Catch:{ all -> 0x0078 }
            if (r0 != 0) goto L_0x008e
            java.lang.Thread.yield()     // Catch:{ all -> 0x0078 }
            goto L_0x0086
        L_0x008e:
            java.lang.Thread.sleep(r14)     // Catch:{ InterruptedException -> 0x0092 }
            goto L_0x0093
        L_0x0092:
            r0 = move-exception
        L_0x0093:
            boolean r0 = r3.selecting     // Catch:{ all -> 0x0078 }
            if (r0 == 0) goto L_0x0086
            long r16 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0078 }
            int r0 = r9.interestOps()     // Catch:{ all -> 0x0078 }
            r18 = r0
            monitor-enter(r3)     // Catch:{ all -> 0x0078 }
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x00c2 }
            r0.wakeup()     // Catch:{ all -> 0x00c2 }
            r0 = r18 | 16
            r9.interestOps(r0)     // Catch:{ all -> 0x00c2 }
            r0 = r18 & -17
            r9.interestOps(r0)     // Catch:{ all -> 0x00c2 }
            monitor-exit(r3)     // Catch:{ all -> 0x00c2 }
            long r19 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0078 }
            long r19 = r19 - r16
            int r0 = (r19 > r12 ? 1 : (r19 == r12 ? 0 : -1))
            if (r0 < 0) goto L_0x00bf
            r0 = 0
            r10 = r0
            goto L_0x00c5
        L_0x00bf:
            int r11 = r11 + 1
            goto L_0x0084
        L_0x00c2:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00c2 }
            throw r0     // Catch:{ all -> 0x0078 }
        L_0x00c5:
            if (r10 == 0) goto L_0x00ca
            r0 = 1
            r4 = r0
            goto L_0x00cc
        L_0x00ca:
            r0 = 2
            r4 = r0
        L_0x00cc:
            if (r2 == 0) goto L_0x00e5
            r2.close()     // Catch:{ all -> 0x00d3 }
            goto L_0x00e5
        L_0x00d3:
            r0 = move-exception
            r7 = r0
            r0 = r7
            org.jboss.netty.logging.InternalLogger r7 = logger
            boolean r7 = r7.isWarnEnabled()
            if (r7 == 0) goto L_0x00e5
            org.jboss.netty.logging.InternalLogger r7 = logger
            java.lang.String r9 = "Failed to close a temporary socket."
            r7.warn(r9, r0)
        L_0x00e5:
            r3.done = r8
            r1.shutdownNow()     // Catch:{ NullPointerException -> 0x00ec }
            goto L_0x00ed
        L_0x00ec:
            r0 = move-exception
        L_0x00ed:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x00fe }
            r0.wakeup()     // Catch:{ all -> 0x00fe }
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x00fc }
            boolean r0 = r1.awaitTermination(r5, r0)     // Catch:{ InterruptedException -> 0x00fc }
            if (r0 == 0) goto L_0x00fd
            goto L_0x00ff
        L_0x00fc:
            r0 = move-exception
        L_0x00fd:
            goto L_0x00ed
        L_0x00fe:
            r0 = move-exception
        L_0x00ff:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x0105 }
            r0.close()     // Catch:{ all -> 0x0105 }
            goto L_0x0115
        L_0x0105:
            r0 = move-exception
            org.jboss.netty.logging.InternalLogger r5 = logger
            boolean r5 = r5.isWarnEnabled()
            if (r5 == 0) goto L_0x0115
            org.jboss.netty.logging.InternalLogger r5 = logger
            java.lang.String r6 = "Failed to close a temporary selector."
            r5.warn(r6, r0)
        L_0x0115:
            return r4
        L_0x0118:
            r0 = move-exception
            r4 = r0
            org.jboss.netty.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0231 }
            boolean r0 = r0.isWarnEnabled()     // Catch:{ all -> 0x0231 }
            if (r0 == 0) goto L_0x0129
            org.jboss.netty.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0231 }
            java.lang.String r9 = "Failed to register a temporary selector."
            r0.warn(r9, r4)     // Catch:{ all -> 0x0231 }
        L_0x0129:
            r9 = r7
            if (r2 == 0) goto L_0x0142
            r2.close()     // Catch:{ all -> 0x0130 }
            goto L_0x0142
        L_0x0130:
            r0 = move-exception
            r10 = r0
            r0 = r10
            org.jboss.netty.logging.InternalLogger r10 = logger
            boolean r10 = r10.isWarnEnabled()
            if (r10 == 0) goto L_0x0142
            org.jboss.netty.logging.InternalLogger r10 = logger
            java.lang.String r11 = "Failed to close a temporary socket."
            r10.warn(r11, r0)
        L_0x0142:
            r3.done = r8
            r1.shutdownNow()     // Catch:{ NullPointerException -> 0x0149 }
            goto L_0x014a
        L_0x0149:
            r0 = move-exception
        L_0x014a:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x015b }
            r0.wakeup()     // Catch:{ all -> 0x015b }
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0159 }
            boolean r0 = r1.awaitTermination(r5, r0)     // Catch:{ InterruptedException -> 0x0159 }
            if (r0 == 0) goto L_0x015a
            goto L_0x015c
        L_0x0159:
            r0 = move-exception
        L_0x015a:
            goto L_0x014a
        L_0x015b:
            r0 = move-exception
        L_0x015c:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x0162 }
            r0.close()     // Catch:{ all -> 0x0162 }
            goto L_0x0172
        L_0x0162:
            r0 = move-exception
            org.jboss.netty.logging.InternalLogger r5 = logger
            boolean r5 = r5.isWarnEnabled()
            if (r5 == 0) goto L_0x0172
            org.jboss.netty.logging.InternalLogger r5 = logger
            java.lang.String r6 = "Failed to close a temporary selector."
            r5.warn(r6, r0)
        L_0x0172:
            r0 = r4
            r4 = -1
            return r4
        L_0x0175:
            r0 = move-exception
            r4 = r0
            org.jboss.netty.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0231 }
            boolean r0 = r0.isWarnEnabled()     // Catch:{ all -> 0x0231 }
            if (r0 == 0) goto L_0x0186
            org.jboss.netty.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0231 }
            java.lang.String r9 = "Failed to open a temporary selector."
            r0.warn(r9, r4)     // Catch:{ all -> 0x0231 }
        L_0x0186:
            r9 = r7
            if (r2 == 0) goto L_0x019f
            r2.close()     // Catch:{ all -> 0x018d }
            goto L_0x019f
        L_0x018d:
            r0 = move-exception
            r10 = r0
            r0 = r10
            org.jboss.netty.logging.InternalLogger r10 = logger
            boolean r10 = r10.isWarnEnabled()
            if (r10 == 0) goto L_0x019f
            org.jboss.netty.logging.InternalLogger r10 = logger
            java.lang.String r11 = "Failed to close a temporary socket."
            r10.warn(r11, r0)
        L_0x019f:
            if (r3 == 0) goto L_0x01d0
            r3.done = r8
            r1.shutdownNow()     // Catch:{ NullPointerException -> 0x01a7 }
            goto L_0x01a8
        L_0x01a7:
            r0 = move-exception
        L_0x01a8:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x01b9 }
            r0.wakeup()     // Catch:{ all -> 0x01b9 }
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x01b7 }
            boolean r0 = r1.awaitTermination(r5, r0)     // Catch:{ InterruptedException -> 0x01b7 }
            if (r0 == 0) goto L_0x01b8
            goto L_0x01ba
        L_0x01b7:
            r0 = move-exception
        L_0x01b8:
            goto L_0x01a8
        L_0x01b9:
            r0 = move-exception
        L_0x01ba:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x01c0 }
            r0.close()     // Catch:{ all -> 0x01c0 }
            goto L_0x01d0
        L_0x01c0:
            r0 = move-exception
            org.jboss.netty.logging.InternalLogger r5 = logger
            boolean r5 = r5.isWarnEnabled()
            if (r5 == 0) goto L_0x01d0
            org.jboss.netty.logging.InternalLogger r5 = logger
            java.lang.String r6 = "Failed to close a temporary selector."
            r5.warn(r6, r0)
        L_0x01d0:
            r0 = r4
            r4 = -1
            return r4
        L_0x01d3:
            r0 = move-exception
            r4 = r0
            org.jboss.netty.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0231 }
            boolean r0 = r0.isWarnEnabled()     // Catch:{ all -> 0x0231 }
            if (r0 == 0) goto L_0x01e4
            org.jboss.netty.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0231 }
            java.lang.String r9 = "Failed to configure a temporary socket."
            r0.warn(r9, r4)     // Catch:{ all -> 0x0231 }
        L_0x01e4:
            r9 = r7
            if (r2 == 0) goto L_0x01fd
            r2.close()     // Catch:{ all -> 0x01eb }
            goto L_0x01fd
        L_0x01eb:
            r0 = move-exception
            r10 = r0
            r0 = r10
            org.jboss.netty.logging.InternalLogger r10 = logger
            boolean r10 = r10.isWarnEnabled()
            if (r10 == 0) goto L_0x01fd
            org.jboss.netty.logging.InternalLogger r10 = logger
            java.lang.String r11 = "Failed to close a temporary socket."
            r10.warn(r11, r0)
        L_0x01fd:
            if (r3 == 0) goto L_0x022e
            r3.done = r8
            r1.shutdownNow()     // Catch:{ NullPointerException -> 0x0205 }
            goto L_0x0206
        L_0x0205:
            r0 = move-exception
        L_0x0206:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x0217 }
            r0.wakeup()     // Catch:{ all -> 0x0217 }
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0215 }
            boolean r0 = r1.awaitTermination(r5, r0)     // Catch:{ InterruptedException -> 0x0215 }
            if (r0 == 0) goto L_0x0216
            goto L_0x0218
        L_0x0215:
            r0 = move-exception
        L_0x0216:
            goto L_0x0206
        L_0x0217:
            r0 = move-exception
        L_0x0218:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x021e }
            r0.close()     // Catch:{ all -> 0x021e }
            goto L_0x022e
        L_0x021e:
            r0 = move-exception
            org.jboss.netty.logging.InternalLogger r5 = logger
            boolean r5 = r5.isWarnEnabled()
            if (r5 == 0) goto L_0x022e
            org.jboss.netty.logging.InternalLogger r5 = logger
            java.lang.String r6 = "Failed to close a temporary selector."
            r5.warn(r6, r0)
        L_0x022e:
            r0 = r4
            r4 = -1
            return r4
        L_0x0231:
            r0 = move-exception
            r4 = r7
            r10 = r7
        L_0x0234:
            r7 = r0
            if (r2 == 0) goto L_0x024e
            r2.close()     // Catch:{ all -> 0x023c }
            goto L_0x024e
        L_0x023c:
            r0 = move-exception
            r9 = r0
            r0 = r9
            org.jboss.netty.logging.InternalLogger r9 = logger
            boolean r9 = r9.isWarnEnabled()
            if (r9 == 0) goto L_0x024e
            org.jboss.netty.logging.InternalLogger r9 = logger
            java.lang.String r11 = "Failed to close a temporary socket."
            r9.warn(r11, r0)
        L_0x024e:
            if (r3 == 0) goto L_0x027f
            r3.done = r8
            r1.shutdownNow()     // Catch:{ NullPointerException -> 0x0256 }
            goto L_0x0257
        L_0x0256:
            r0 = move-exception
        L_0x0257:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x0268 }
            r0.wakeup()     // Catch:{ all -> 0x0268 }
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0266 }
            boolean r0 = r1.awaitTermination(r5, r0)     // Catch:{ InterruptedException -> 0x0266 }
            if (r0 == 0) goto L_0x0267
            goto L_0x0269
        L_0x0266:
            r0 = move-exception
        L_0x0267:
            goto L_0x0257
        L_0x0268:
            r0 = move-exception
        L_0x0269:
            java.nio.channels.Selector r0 = r3.selector     // Catch:{ all -> 0x026f }
            r0.close()     // Catch:{ all -> 0x026f }
            goto L_0x027f
        L_0x026f:
            r0 = move-exception
            org.jboss.netty.logging.InternalLogger r5 = logger
            boolean r5 = r5.isWarnEnabled()
            if (r5 == 0) goto L_0x027f
            org.jboss.netty.logging.InternalLogger r5 = logger
            java.lang.String r6 = "Failed to close a temporary selector."
            r5.warn(r6, r0)
        L_0x027f:
            r0 = r7
            r5 = -1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.socket.nio.NioProviderMetadata.autodetect():int");
    }

    private static final class SelectorLoop implements Runnable {
        volatile boolean done;
        volatile boolean selecting;
        final Selector selector = Selector.open();

        SelectorLoop() throws IOException {
        }

        public void run() {
            while (!this.done) {
                synchronized (this) {
                }
                try {
                    this.selecting = true;
                    this.selector.select(1000);
                    this.selecting = false;
                    Set<SelectionKey> keys = this.selector.selectedKeys();
                    for (SelectionKey k : keys) {
                        k.interestOps(0);
                    }
                    keys.clear();
                } catch (IOException e) {
                    if (NioProviderMetadata.logger.isWarnEnabled()) {
                        NioProviderMetadata.logger.warn("Failed to wait for a temporary selector.", e);
                    }
                } catch (Throwable th) {
                    this.selecting = false;
                    throw th;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
            PrintStream printStream = System.out;
            printStream.println(e.getKey() + ": " + e.getValue());
        }
        System.out.println();
        PrintStream printStream2 = System.out;
        printStream2.println("Hard-coded Constraint Level: " + CONSTRAINT_LEVEL);
        PrintStream printStream3 = System.out;
        printStream3.println("Auto-detected Constraint Level: " + autodetect());
    }

    private NioProviderMetadata() {
    }
}
