package pia.beans;

import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Post;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;

@Named
@Stateless
public class PostRecommenderService implements Serializable {
    @Inject
    Principal principal;

    @Inject
    @JPADAO
    PostDao pd;

    public List<Post> getRecommended() {
        // TODO: cache it
        return pd.getMostPopularDay();
    }
}
