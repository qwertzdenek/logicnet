package pia.rest;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import pia.beans.RegisterService;
import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;
import pia.rest.entities.AccountEntity;
import pia.ServiceResult;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class AccountResource implements Serializable {
    private JsonGeneratorFactory jsonFactory;

    @EJB
    private RegisterService rs;

    @Inject
    @JPADAO
    private AccountDao ad;

    public AccountResource() {
        jsonFactory = Json.createGeneratorFactory(null);
    }

    @GET
    @RolesAllowed("user")
    public Response getAccounts() {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = jsonFactory.createGenerator(sw);

        List<Account> accountList = ad.listAll();

        if (accountList.isEmpty()) {
            return Response.noContent().build();
        }

        gen.writeStartArray();
        for (Account a : accountList) {
            gen.writeStartObject();
            gen.write("nickname", a.getId());
            gen.write("real_name", a.getName());
            gen.write("profile", a.getProfilePicture());
            gen.write("birthday", new SimpleDateFormat("dd/MM/yyyy").format(a.getBirthday()));
            gen.write("likes", a.getLikedPosts().size());
            gen.write("posts", a.getPosts().size());
            gen.writeEnd();
        }
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

    @GET
    @RolesAllowed("user")
    @Path("{nickname}")
    public Response getAccount(
            @PathParam("nickname")
            @Pattern(regexp = "\\w+", message = "This is not valid nickname.'")
            String id) {
        Account account = ad.findOne(id);

        if (account == null) {
            return Response.noContent().entity("{'message': 'account don\'t exist'}").build();
        }

        AccountEntity ae = new AccountEntity();

        ae.setNickname(account.getId());
        ae.setReal_name(account.getName());
        ae.setBirthday(account.getBirthday().toString());
        ae.setPassword("HIDDEN");

        return Response.ok(ae, MediaType.APPLICATION_JSON_TYPE).build();
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
}
