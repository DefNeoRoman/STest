import java.util.Comparator;

/**
 * Created by роман on 28.04.2017.
 */
public class EntityComparator  implements Comparator<Entity> {
    public int compare(Entity a, Entity b) {
        return a.getCreatedDate().compareTo(b.getCreatedDate());
    }
}
