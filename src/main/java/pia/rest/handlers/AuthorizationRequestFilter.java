package pia.rest.handlers;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationRequestFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!hasUserAccess(requestContext.getSecurityContext())) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    public boolean hasUserAccess(SecurityContext context) {
        Method method = resourceInfo.getResourceMethod();
        if (method.getAnnotation(DenyAll.class) != null) {
            return false;
        }

        if (method.getAnnotation(PermitAll.class) != null) {
            return true;
        }

        RolesAllowed raMethod = method.getAnnotation(RolesAllowed.class);
        if (raMethod != null) {
            for (String role : raMethod.value()) {
                if (context.isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }

        Class<?> resourceClass = resourceInfo.getResourceClass();
        if (resourceClass.getAnnotation(DenyAll.class) != null) {
            return false;
        }

        if (resourceClass.getAnnotation(PermitAll.class) != null) {
            return true;
        }

        RolesAllowed raClass = resourceClass.getAnnotation(RolesAllowed.class);
        if (raClass != null) {
            for (String role : raClass.value()) {
                if (context.isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
