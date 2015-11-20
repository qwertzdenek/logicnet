package pia.data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {
    private String content;
    private Date date;

    public Post(String content, Date date) {
        this.content = content;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!content.equals(post.content)) return false;
        return date.equals(post.date);

    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return content;
    }
}
