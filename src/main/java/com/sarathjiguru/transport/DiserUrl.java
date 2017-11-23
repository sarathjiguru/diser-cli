package com.sarathjiguru.transport;

/**
 * Created by sarath on 15/11/17.
 * Splits DiserUrl and sets host and port
 */
public class DiserUrl {
    private final String host;
    private final int port;
    private final String r;

    public DiserUrl(String r) {
        this.r = r;
        String hostPort = r.replace("diser://", "").replace("/", "");
        String[] split = hostPort.split(":");
        this.host = split[0];
        this.port = Integer.parseInt(split[1]);
    }

    public int port() {
        return port;
    }

    public String host() {
        return host;
    }

    @Override
    public String toString() {
        return r;
    }
}
