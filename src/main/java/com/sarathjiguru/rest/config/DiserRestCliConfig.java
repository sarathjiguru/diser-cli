package com.sarathjiguru.rest.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarathjiguru.rest.store.DiserDB;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p/>
 * DiserRestCli Configuration file. Contains objects for YAML config sections.
 */
public class DiserRestCliConfig extends Configuration {

    /**
     * Diser Host URL.
     */
    @NotEmpty
    @JsonProperty("diser.store")
    private String diserStore;


    public void setDiserStore(String mongoURL) {
        this.diserStore = mongoURL;
    }

    public DiserDB getDiserDB() throws InterruptedException {
        return new DiserDB(diserStore);
    }

    @Override
    public String toString() {
        return diserStore;
    }
}
