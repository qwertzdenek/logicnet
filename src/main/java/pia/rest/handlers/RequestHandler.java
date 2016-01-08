package pia.rest.handlers;

import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;

import javax.ws.rs.core.Response;

public interface RequestHandler {
    Response handleRequest(Message inputMessage,
                           ClassResourceInfo resourceClass);

}
