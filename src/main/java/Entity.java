import java.util.Date;

/**
 * Created by роман on 28.04.2017.
 */
public class Entity {
    private String fileName;
    private Date createdDate;
    private long size;
    public Entity(String fileName, Date createdDate, long size) {
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.size = size;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    @Override
    public String toString() {
        return
                "[file" + fileName +
                " date=" + createdDate +
                " size=" + size +
                ']';
    }
}
