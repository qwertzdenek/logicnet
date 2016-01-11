package pia.cdi;

import pia.beans.PostRecommenderService;
import pia.data.Post;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.Principal;
import java.util.List;

@Named
@RequestScoped
public class StreamController {
    private static final int PAGING = 8;

    @EJB
    private PostRecommenderService recommender;

    @Inject
    Principal principal;

    @Inject
    private StreamModel model;

    public void clearTag() {
        model.setTag(null);
    }

    public void update() {
        List<Post> newRec;
        if (model.getTag() == null) {
            newRec = recommender.getRecommended(principal.getName());
        } else {
            newRec = recommender.getRecommended(principal.getName(), model.getTag());
        }

        int pageCount = (int) Math.ceil((float) newRec.size() / PAGING);
        pageCount = Math.max(pageCount, 1);
        model.setPageCount(pageCount);

        int page = Math.max(0, model.getPage());
        page = Math.min(pageCount - 1, page);

        // default value for next request is 0
        model.setPage(0);

        model.setPreviousPage(page-1);
        model.setNextPage(page+1);

        int firstIndex = page*PAGING;
        int lastIndex = Math.min(firstIndex + PAGING, newRec.size());
        model.setRecommendation(newRec.subList(firstIndex, lastIndex));

        model.setFirstPage(page==0);

        boolean lastPage = (page + 1) * PAGING >= newRec.size();
        model.setLastPage(lastPage);
    }
}
