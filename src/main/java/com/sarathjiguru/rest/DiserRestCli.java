package com.sarathjiguru.rest;


import com.codahale.metrics.health.HealthCheck;
import com.sarathjiguru.rest.config.DiserRestCliConfig;
import com.sarathjiguru.rest.healthcheck.DiserHealthCheck;
import com.sarathjiguru.rest.resources.GetCommand;
import com.sarathjiguru.rest.resources.SetCommand;
import com.sarathjiguru.transport.DiserTransportCli;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.SortedMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiserRestCli extends Application<DiserRestCliConfig> {

    public static ExecutorService executor;
    public static DiserTransportCli nativeDiser;

    @Override
    public void run(DiserRestCliConfig diserCliConfig, Environment environment) throws Exception {

        DiserHealthCheck hc = new DiserHealthCheck(diserCliConfig.getDiserDB());
        environment.healthChecks().register("diser.store", hc);
        SortedMap<String, HealthCheck.Result> healthChecks = environment.healthChecks().runHealthChecks();

        for (String entry : healthChecks.keySet()) {
            if (!healthChecks.get(entry).isHealthy()) {
                throw new Exception(entry + " can not be initialized", healthChecks.get(entry).getError());
            }
        }

        nativeDiser = diserCliConfig.getDiserDB().build(environment);
        executor = Executors.newFixedThreadPool(24);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        SetCommand setCommand = new SetCommand();
        environment.jersey().register(setCommand);
        environment.jersey().register(new GetCommand());

    }

    public static void main(String args[]) throws Exception {
        new DiserRestCli().run(args);
    }
}
