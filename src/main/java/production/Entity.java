package production;

import java.io.Serializable;
import java.util.Date;

public class Entity implements Serializable, Comparable<Entity> {
    private String fileName; //все поля файнал
    private Date createdDate;
    private long size;
    public Entity(String fileName, Date createdDate, long size) {
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.size = size;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public long getSize() {
        return size;
    }
    @Override
    public String toString() {
        return
                "[file = " + fileName +
                        " date = " + createdDate +
                        " size = " + size +
                        ']' + "\n";
    }
    @Override
    public int compareTo(Entity e) {
        return (int) (createdDate.getTime() - e.getCreatedDate().getTime());//Сортируем по дате
    }
}
