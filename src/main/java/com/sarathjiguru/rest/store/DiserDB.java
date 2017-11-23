package com.sarathjiguru.rest.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarathjiguru.transport.DiserClient;
import com.sarathjiguru.transport.DiserTransportCli;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * <p/>
 * Generic Class to initialize a DiserTransportClient given the Diser URL
 */
public class DiserDB {

    /**
     * Diser URL to connect.
     * Example: diser://hsot:port/
     */
    @JsonProperty("diserURL")
    private String diserURL;


    /**
     * DiserTransportClient
     */
    private final DiserTransportCli diserTCli;

    public DiserDB(String diserURL) throws InterruptedException {
        this.diserTCli = DiserTransportCli.connect(diserURL);
    }

    public DiserTransportCli diserCli(){
        return diserTCli;
    }


    /**
     * .
     *
     * @param environment Dropwizard Environment
     * @return jongo Jongo object created out of @this
     * @see DiserTransportCli
     */
    public DiserTransportCli build(Environment environment) {
        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() {
                // do nothing for now.
            }

            @Override
            public void stop() throws InterruptedException {
                diserTCli.disconnect();
            }
        });
        return diserTCli;
    }

    @Override
    public String toString() {
        return diserURL;
    }

}
