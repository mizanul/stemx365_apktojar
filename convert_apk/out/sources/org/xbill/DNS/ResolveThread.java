package org.xbill.DNS;

class ResolveThread extends Thread {

    /* renamed from: id */
    private Object f213id;
    private ResolverListener listener;
    private Message query;
    private Resolver res;

    public ResolveThread(Resolver res2, Message query2, Object id, ResolverListener listener2) {
        this.res = res2;
        this.query = query2;
        this.f213id = id;
        this.listener = listener2;
    }

    public void run() {
        try {
            this.listener.receiveMessage(this.f213id, this.res.send(this.query));
        } catch (Exception e) {
            this.listener.handleException(this.f213id, e);
        }
    }
}
