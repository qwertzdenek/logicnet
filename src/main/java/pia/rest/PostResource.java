package pia.rest;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Account;
import pia.data.Post;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringWriter;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Path("post")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@ConcurrencyManagement(value = ConcurrencyManagementType.CONTAINER)
public class PostResource {
    private JsonGeneratorFactory jsonFactory;

    @Inject
    @JPADAO
    private PostDao pd;

    @Inject
    @JPADAO
    private AccountDao ad;

    public PostResource() {
        jsonFactory = Json.createGeneratorFactory(null);
    }

    @POST
    @RolesAllowed("user")
    public Response addPost(
            @Context HttpServletRequest req,
            /*@Pattern(regexp = "[\\w\\s.!]+", message = "This is not valid content.'")*/
            String content) {
        Account writer = ad.findOne(req.getUserPrincipal().getName());

        Post post = new Post();
        post.setContent(content);
        post.setDateTime(Timestamp.from(Instant.now()));
        post.setWriter(writer);
        post.setLikes(new LinkedHashSet<>());
        post.setLikesCount(0);

        pd.save(post);

        return Response.accepted().build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("like/{post}")
    @Lock(LockType.WRITE)
    public Response likePost(
            @Context HttpServletRequest req,
            @PathParam("post") long postId) {
        Account writer = ad.findOne(req.getUserPrincipal().getName());

        Post post = pd.findOne(postId);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (post.getLikes().contains(writer)) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }

        int count = pd.addLike(post, writer);

        StringWriter sw = new StringWriter();
        JsonGenerator gen = jsonFactory.createGenerator(sw);

        gen.writeStartObject();
        gen.write("post", post.getId());
        gen.write("likes", count);
        gen.writeEnd();

        gen.close();

        return Response.accepted(sw.toString()).build();
    }

    @GET
    @Path("list/{nickname}")
    public Response listPosts(
            @PathParam("nickname")
            @Pattern(regexp = "[a-zA-Z]+", message = "This is not valid nickname")
            String nickname) {
        Account account = ad.findOne(nickname);

        if (account == null) {
            return Response.noContent().entity("{'message': 'account don\'t exist'}").build();
        }

        Set<Post> posts = account.getPosts();

        StringWriter sw = new StringWriter();
        JsonGenerator gen = jsonFactory.createGenerator(sw);

        gen.writeStartArray();
        for (Post post : posts) {
            gen.writeStartObject();
            gen.write("id", post.getId());
            gen.write("date", new SimpleDateFormat("dd/MM/yyyy m:s").format(post.getDateTime()));
            gen.write("content", post.getContent());
            gen.write("likes", post.getLikes().size());
            gen.writeEnd();
        }
        gen.writeEnd();

        gen.close();

        return Response.ok(sw.toString()).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("hide/{post}")
    public Response hidePost(
            @Context HttpServletRequest req,
            @PathParam("post") long postId) {
        Account who = ad.findOne(req.getUserPrincipal().getName());

        Post post = pd.findOne(postId);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (who.getPostHides().contains(post)) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }

        who.getPostHides().add(post);
        ad.save(who);

        return Response.ok().build();
    }
}
