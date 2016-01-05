package pia.rest;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Account;
import pia.data.Post;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
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
public class PostResource {
    private JsonGeneratorFactory jsonFactory;

    @Inject
    @JPADAO
    private PostDao pd;

    @Inject
    @JPADAO
    private AccountDao ad;

    @Inject
    private Principal principal;

    public PostResource() {
        jsonFactory = Json.createGeneratorFactory(null);
    }

    @POST
    @RolesAllowed("user")
    public Response addPost(
            /*@Pattern(regexp = "[\\w\\s.!]+", message = "This is not valid content.'")*/
            String content) {
        Account writer = ad.findOne(principal.getName());

        if (writer == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        // TODO: escape values

        Post post = new Post();
        post.setContent(content);
        post.setDateTime(Timestamp.from(Instant.now()));
        post.setWriter(writer);
        post.setLikes(new LinkedHashSet<>());

        writer.addPost(post);

        pd.save(post);
        ad.save(writer);

        return Response.accepted().build();
    }

    @POST
    @RolesAllowed("user")
    @Path("like/{post_id}")
    public Response likePost(@PathParam("post_id") long postId) {
        Account writer = ad.findOne(principal.getName());

        if (writer == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Post post = pd.findOne(postId);

        if (post == null) {
            return Response.noContent().entity("{'message': 'post don\'t exist'}").build();
        }

        post.addLike(writer);
        writer.addLike(post);

        pd.save(post);
        ad.save(writer);

        return Response.accepted().build();
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
}
