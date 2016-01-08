package pia.rest.handlers;

import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.Method;

public class AuthorizationRequestHandler implements RequestHandler {
    @Context
    private SecurityContext securityContext;

    @Override
    public Response handleRequest(Message msg, ClassResourceInfo cri) {
        if (!hasUserAccess(msg, cri)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return null;
    }

    public boolean hasUserAccess(Message msg, ClassResourceInfo cri) {
        /*
         * Check method attributes
         */
        Method method = msg.getExchange().get(OperationResourceInfo.class).getAnnotatedMethod();
        if (method.getAnnotation(DenyAll.class) != null) {
            return false;
        }

        if (method.getAnnotation(PermitAll.class) != null) {
            return true;
        }

        RolesAllowed raMethod = method.getAnnotation(RolesAllowed.class);
        if (raMethod != null) {
            for (String role : raMethod.value()) {
                if (securityContext.isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }

        /*
         * Check class attributes
         */
        Class<?> resourceClass = cri.getResourceClass();
        if (resourceClass.getAnnotation(DenyAll.class) != null) {
            return false;
        }

        if (resourceClass.getAnnotation(PermitAll.class) != null) {
            return true;
        }

        RolesAllowed raClass = resourceClass.getAnnotation(RolesAllowed.class);
        if (raClass != null) {
            for (String role : raClass.value()) {
                if (securityContext.isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
