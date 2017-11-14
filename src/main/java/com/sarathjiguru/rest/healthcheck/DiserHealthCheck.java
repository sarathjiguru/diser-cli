package com.sarathjiguru.rest.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.sarathjiguru.rest.store.DiserDB;
import com.sarathjiguru.transport.DiserTransportCli;

/**
 * Check Diser before running the service
 */
public class DiserHealthCheck extends HealthCheck {
    private final DiserTransportCli nativeClient;

    public DiserHealthCheck(DiserDB diserDB) {
        this.nativeClient = diserDB.diserCli();
    }

    /**
     * Ping the Diser and respond appropriately
     *
     * @return HealthCheck.Result
     * @throws Exception
     */
    @Override
    protected Result check() throws Exception {
        try {
           // nativeClient.runCommand("@ping");
        } catch (Exception e) {
            return Result.unhealthy(e);
        } finally {
            nativeClient.disconnect();
        }
        return Result.healthy();
    }
}
