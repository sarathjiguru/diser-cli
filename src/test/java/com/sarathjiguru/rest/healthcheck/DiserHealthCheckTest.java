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
        DiserDB diserDB = new DiserDB("diser://127.0.0.1:8007");
        DiserHealthCheck diserHealthCheck = new DiserHealthCheck(diserDB);
        System.out.println(diserHealthCheck.check().getMessage());
    }

}