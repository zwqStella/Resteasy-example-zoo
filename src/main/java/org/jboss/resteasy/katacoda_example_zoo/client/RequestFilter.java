package org.jboss.resteasy.katacoda_example_zoo.client;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.jboss.logging.Logger;
import org.jboss.resteasy.util.DelegatingOutputStream;

public class RequestFilter implements ClientRequestFilter {
    private static final Logger LOG = Logger.getLogger(RequestFilter.class.getName());

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        if (requestContext.hasEntity()) {
            try {
                LOG.info(requestContext.getEntity());
                DelegatingOutputStream entity = (DelegatingOutputStream) requestContext.getEntityStream();
                LOG.info("Sent: " + new String(entity.getDelegate().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
