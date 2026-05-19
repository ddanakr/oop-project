package universitysystem.models.news;

import java.io.Serializable;

public interface Subscriber extends Serializable {
    void update(String notification);
}
