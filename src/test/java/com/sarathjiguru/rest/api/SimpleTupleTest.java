package com.sarathjiguru.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by sarath on 14/11/17.
 */
public class SimpleTupleTest {

    private SimpleTuple simpleTuple;

    @Before
    public void setUp() throws IOException {
        simpleTuple = new ObjectMapper().readValue("{\"key\":\"a\", \"value\":12332}", SimpleTuple.class);
    }

    @Test
    public void getKey() throws Exception {
        assertEquals(simpleTuple.getKey(), "a");
    }

    @Test
    public void getValue() throws Exception {
        assertEquals(simpleTuple.getValue(), 12332);
    }

}