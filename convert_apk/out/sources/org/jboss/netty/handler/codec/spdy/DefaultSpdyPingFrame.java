package org.jboss.netty.handler.codec.spdy;

import org.jboss.netty.util.internal.StringUtil;

public class DefaultSpdyPingFrame implements SpdyPingFrame {

    /* renamed from: id */
    private int f140id;

    public DefaultSpdyPingFrame(int id) {
        setId(id);
    }

    public int getID() {
        return getId();
    }

    public int getId() {
        return this.f140id;
    }

    public void setID(int id) {
        setId(id);
    }

    public void setId(int id) {
        this.f140id = id;
    }

    public String toString() {
        return getClass().getSimpleName() + StringUtil.NEWLINE + "--> ID = " + this.f140id;
    }
}
