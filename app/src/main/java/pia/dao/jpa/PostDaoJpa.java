package pia.dao.jpa;

import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Post;

@JPADAO
public class PostDaoJpa extends GenericDaoJpa<Post, Long> implements PostDao {
    public PostDaoJpa() {
        super(Post.class);
    }
}
