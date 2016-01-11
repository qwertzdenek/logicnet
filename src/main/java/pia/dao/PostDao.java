package pia.dao;

import pia.data.Account;
import pia.data.Post;

import javax.persistence.NoResultException;
import java.util.List;

public interface PostDao extends GenericDao<Post, Long> {
    List<Post> getMostPopularDay();
    List<Post> getLatestPosts();
    List<Post> getLatestPosts(Account account);
    List<Post> getLatestPosts(Account account, int count);
    List<Post> getLatestPostsWith(String tag);

    void addLike(Post post, Account who);
}
