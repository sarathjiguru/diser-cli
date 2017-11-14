package com.sarathjiguru.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 */
public class DiserRestCliConfigurationTest {

    private DiserRestCliConfig diserRestCliConfig;
    YAMLFactory yf = new YAMLFactory();
    private URL url;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = Jackson.newObjectMapper(yf);
        diserRestCliConfig = objectMapper.readValue(this.getClass().getClassLoader().getResource("diserconfig.yml"),
                DiserRestCliConfig.class);
    }

    @Test
    public void testGetMongoStore() throws Exception {
        assertNotNull(diserRestCliConfig);
    }
}