package com.sarathjiguru.rest.resources;

import com.sarathjiguru.rest.DiserRestCli;
import com.sarathjiguru.rest.config.constants.PathConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

/**
 * Created by sarath on 15/11/17.
 */
@Path("v1/get")
@Produces(MediaType.APPLICATION_JSON)
public class GetCommand {

    @GET
    @Path("/key{" + PathConstants.KEY + "}")
    public Object get(@PathParam(PathConstants.KEY) String key) throws ExecutionException, InterruptedException {
        Object result = DiserRestCli.nativeDiser.runCommand("GET$" + key);
        return result;
    }
}
