package com.sarathjiguru.rest.healthcheck;

import com.sarathjiguru.rest.store.DiserDB;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sarath on 14/11/17.
 */
public class DiserHealthCheckTest {
    @Test
    public void check() throws Exception {
        DiserHealthCheck diserHealthCheck = new DiserHealthCheck(new DiserDB("diser://127.0.0.1:8007"));
        System.out.println(diserHealthCheck.check().getMessage());
    }

}