package pia.rest;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import pia.ServiceResult;
import pia.beans.RegisterService;
import pia.dao.AccountDao;
import pia.dao.FriendshipDao;
import pia.dao.JPADAO;
import pia.data.Account;
import pia.rest.entities.AccountEntity;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.util.List;

@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class AccountResource implements Serializable {
    private JsonGeneratorFactory jsonFactory;

    @EJB
    private RegisterService rs;

    @Inject
    @JPADAO
    private AccountDao ad;

    @Inject
    @JPADAO
    private FriendshipDao fd;

    public AccountResource() {
        jsonFactory = Json.createGeneratorFactory(null);
    }

    @GET
    @RolesAllowed("user")
    @Produces("text/plain")
    @Path("logout")
    public Response logout(@Context HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
        return Response.ok().entity("You have been successfully logged out.").build();
    }

    @GET
    @RolesAllowed("user")
    public Response getAccount(@Context HttpServletRequest req) {
        Account a = ad.findOne(req.getUserPrincipal().getName());

        StringWriter sw = new StringWriter();
        JsonGenerator gen = jsonFactory.createGenerator(sw);

        gen.writeStartObject();
        gen.write("nickname", a.getId());
        gen.write("real_name", a.getName());
        gen.write("profile", "/images/"+a.getProfilePicture());

        Date birthday = a.getBirthday();
        gen.write("birthday", new SimpleDateFormat("dd/MM/yyyy").format(birthday));

        LocalDate localBirthday = birthday.toLocalDate();
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Prague"));
        long age = ChronoUnit.YEARS.between(localBirthday, today);
        gen.write("age", age);

        gen.write("likes", ad.likesCount(a));
        gen.write("posts", ad.postCount(a));
        gen.writeStartArray("friend_requests");
        for (Account fr : a.getIncomingFriendRequests()) {
            gen.writeStartObject();
            gen.write("nickname", fr.getId());
            gen.write("real_name", fr.getName());
            gen.writeEnd();
        }
        gen.writeEnd();
        gen.writeStartArray("friends");
        for (Account fr : fd.allFriends(a)) {
            gen.writeStartObject();
            gen.write("nickname", fr.getId());
            gen.write("real_name", fr.getName());
            gen.writeEnd();
        }
        gen.writeEnd();
        gen.writeEnd();

        gen.close();

        String result = sw.toString();
        try {
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(result).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createAccount(
            @Multipart(value = "metadata", type = MediaType.APPLICATION_JSON)
              @Valid AccountEntity account,
            @Multipart(value = "image")
              Attachment image) {

        ServiceResult res = rs.register(account, image);
        if (res.getSuccess()) {
            return Response.accepted().build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(res.getMessage()).build();
        }
    }

    @POST
    @RolesAllowed("user")
    @Path("friend/{nickname}")
    public Response friendRequest(
            @Context HttpServletRequest req,
            @PathParam("nickname") String nickname) {
        Account account = ad.findOne(req.getUserPrincipal().getName());

        Account target = ad.findOne(nickname);
        if (target == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (fd.allFriends(account).contains(target) || target.getIncomingFriendRequests().contains(account)) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }

        if (target.equals(account)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        account.getFriendRequests().add(target);
        ad.save(account);

        return Response.ok().build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("friend/{nickname}")
    public Response acceptFriendship(
            @Context HttpServletRequest req,
            @PathParam("nickname") String nickname) {
        Account account = ad.findOne(req.getUserPrincipal().getName());

        Account target = ad.findOne(nickname);
        if (target == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // we can't add someone who haven't requested us
        if (!account.getIncomingFriendRequests().contains(target)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        if (fd.allFriends(account).contains(target)) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }

        fd.addFriendship(target, account);

        return Response.ok().build();
    }
}
