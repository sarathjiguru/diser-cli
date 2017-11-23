package com.sarathjiguru.transport;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sarath on 23/11/17.
 */
public class DiserUrlTest {

    private DiserUrl diserUrl;

    @Before
    public void setup(){
        diserUrl = new DiserUrl("diser://127.0.0.1:7443/");
    }
    @Test
    public void port() throws Exception {
        System.out.println(diserUrl.port());
    }

    @Test
    public void host() throws Exception {
        System.out.println(diserUrl.host());
    }

}