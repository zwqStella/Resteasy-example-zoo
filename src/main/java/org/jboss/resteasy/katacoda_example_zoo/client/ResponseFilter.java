package org.jboss.resteasy.katacoda_example_zoo.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;

public class ResponseFilter implements ClientResponseFilter {
    private static final Logger LOG = Logger.getLogger(ResponseFilter.class.getName());

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        if (responseContext.hasEntity()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(responseContext.getEntityStream(), baos);
            byte[] bytes = baos.toByteArray();
            LOG.info("Received: " + new String(bytes, "UTF-8"));
            responseContext.setEntityStream(new ByteArrayInputStream(bytes));
        }
    }

}
