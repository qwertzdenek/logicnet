package pia.cdi;

import pia.data.Post;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class StreamModel implements Serializable {
    private int page = 0;
    private String tag;
    private int pageCount;
    private boolean firstPage;
    private boolean lastPage;
    private int nextPage;
    private int previousPage;
    private List<Post> recommendation;

    public int getPage() { return page; }

    public void setPage(int page) {
        this.page = page;
    }

    @Pattern(regexp = "\\p{L}+")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public List<Post> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<Post> recommendation) {
        this.recommendation = recommendation;
    }
}
