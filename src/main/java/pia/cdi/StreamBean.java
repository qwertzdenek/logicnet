package pia.cdi;

import pia.beans.PostRecommenderService;
import pia.data.Post;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class StreamBean implements Serializable{
    private static final int PAGING = 8;

    @EJB
    private PostRecommenderService recommender;

    private List<Post> recomendation = new ArrayList<>();

    private Instant streamTimestamp = Instant.MIN;
    private String topic;
    private int page;
    private int pageCount;

    @Pattern(regexp = "\\w+")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean getLastPage() {
        return (page + 1) * PAGING >= recomendation.size();
    }

    public List<Post> getRecomendation() {
        long actualTime = Instant.now().getEpochSecond();

        if (actualTime - streamTimestamp.getEpochSecond() > 60L) {
            recomendation.clear();
            recomendation.addAll(recommender.getRecommended());

            streamTimestamp = Instant.ofEpochSecond(actualTime);
        }

        pageCount = (int) Math.ceil((float) recomendation.size() / PAGING);
        pageCount = Math.max(pageCount, 1);

        page = Math.max(0, page);
        page = Math.min(pageCount - 1, page);

        int firstIndex = page*PAGING;
        int lastIndex = Math.min(firstIndex + PAGING, recomendation.size());
        return recomendation.subList(firstIndex, lastIndex);
    }
}
