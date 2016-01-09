package pia.dao;

import pia.data.Account;
import pia.data.Post;

import java.util.List;

/**
 * DAO interface for the User entity
 * <p>
 * Date: 26.9.15
 *
 * @author Jakub Danek
 */
public interface PostDao extends GenericDao<Post, Long> {
    List<Post> getMostPopularDay();
    List<Post> getLastPosts();
    List<Post> getLatestFrom(Account account);
    int addLike(Post post, Account who);
}
