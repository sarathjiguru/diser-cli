package com.sarathjiguru.rest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarathjiguru.rest.DiserRestCli;
import com.sarathjiguru.rest.api.SimpleTuple;
import com.sarathjiguru.transport.DiserClient;
import com.sarathjiguru.transport.DiserTransportCli;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by sarath on 14/11/17.
 */
public class SetCommandTest {
    @Before
    public void setUp() throws Exception {
        DiserRestCli.nativeDiser = DiserTransportCli.connect(DiserClient.HOST, DiserClient.PORT);
    }

    @After
    public void tearDown() throws Exception {
        DiserRestCli.nativeDiser.disconnect();
    }

    @Test
    public void set() throws Exception {
        SetCommand setCommand = new SetCommand();
        SimpleTuple simpleTuple = new ObjectMapper().readValue("{\"key\":\"a\", \"value\":1}", SimpleTuple.class);
        System.out.println(setCommand.set(simpleTuple));
    }

}