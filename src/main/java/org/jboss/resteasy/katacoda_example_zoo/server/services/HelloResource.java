package org.jboss.resteasy.katacoda_example_zoo.server.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class HelloResource {

    @GET
    @Produces("text/plain")
    public String sayHello() {
        // generate your code here
        return "welcome\n";
    }

}
