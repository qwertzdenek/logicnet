package pia.beans;

import pia.data.Post;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Named;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.LinkedHashSet;

@Named
@Singleton
public class TrendingService {
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

        // TODO: get it from the JPA

        Post a = new Post();
        a.setId(12L);
        a.setContent("Lorem ipsum");
        a.setDateTime(Timestamp.from(Instant.now()));
        trending.add(a);

        Post b = new Post();
        b.setId(10L);
        b.setContent("Duis ac metus ut neque pretium imperdiet. Quisque fringilla nulla erat, at vehicula ipsum auctor ac. Proin eu tristique nunc. Donec tempor in velit gravida mollis. Duis congue iaculis velit finibus faucibus. Duis lorem massa, luctus in metus sit amet, rhoncus interdum arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;");
        b.setDateTime(Timestamp.from(Instant.now().minusMillis(1000L*60L*60L*4)));

        trending.add(b);
    }
}
