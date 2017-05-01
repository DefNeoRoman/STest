import java.util.Comparator;
public class EntityComparator  implements Comparator<Entity> {
    public int compare(Entity a, Entity b) {
        return a.getCreatedDate().compareTo(b.getCreatedDate());
    }
}
