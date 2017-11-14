package com.sarathjiguru.rest.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarathjiguru.rest.DiserRestCli;
import com.sarathjiguru.rest.api.SimpleTuple;
import com.sarathjiguru.rest.config.constants.PathConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

/**
 * Diser SET Command. SET key and value in DiserDB
 */
@Path("/v1/set")
@Produces(MediaType.APPLICATION_JSON)

public class SetCommand {

    public static final String SPACE = "_space";
    private final Logger logger;

    public SetCommand() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * POST Request
     * Endpoint: /v1/set/:key
     * Service to upload parking spaces to the spaces DB.
     */
    @POST
    @Path("/set/key")
    public Object set(SimpleTuple tuple)
            throws ExecutionException, InterruptedException, JsonProcessingException {
        Object result = DiserRestCli.nativeDiser.runCommand("SET$" + tuple.getKey() + "$" + tuple.getValue());
        return result;
    }
}
