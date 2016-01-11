package pia.beans;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Account;
import pia.data.Post;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@Stateless
public class PostRecommenderService implements Serializable {
    @Inject
    @JPADAO
    AccountDao ad;

    @Inject
    @JPADAO
    PostDao pd;

    public List<Post> getRecommended(String a) {
        List<Post> recommended = pd.getLatestPosts();
        recommended.removeAll(ad.findOne(a).getPostHides());

        return recommended;
    }

    public List<Post> getRecommended(String a, String tag) {
        List<Post> recommended = pd.getLatestPostsWith(tag);
        recommended.removeAll(ad.findOne(a).getPostHides());

        return recommended;
    }
}
