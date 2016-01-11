package pia.beans;

import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Post;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedHashSet;

@Named
@Singleton
public class TrendingService {
    @Inject
    @JPADAO
    private PostDao pd;

    private LinkedHashSet<Post> trending = new LinkedHashSet<>();

    public LinkedHashSet<Post> getTrending() {
        return trending;
    }

    public void setTrending(LinkedHashSet<Post> trending) {
        this.trending = trending;
    }

    @Schedule(second="*/20", minute="*",hour="*", persistent=false)
    public void doUpdate() {
        trending.clear();
        trending.addAll(pd.getMostPopularDay());
    }
}
