//Убрать тесты на Entity, так как никто не проверяет никогда геттеры и сеттеры
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;
public class EntityTest {
    private static  Entity en = new Entity();
    @Test
    public void getFileName() throws Exception {
        en.setFileName("testName");
        assertEquals("testName", en.getFileName());
    }
    @Test
    public void setFileName() throws Exception {
        en.setFileName("newName");
        assertEquals("newName", en.getFileName());
    }
   @Test
    public void getCreatedDate() throws Exception {
        en.setCreatedDate(new Date(500));
        assertEquals(new Date(500), en.getCreatedDate());
    }
    @Test
    public void setCreatedDate() throws Exception {
        en.setCreatedDate(new Date(600));
        assertEquals(new Date(600), en.getCreatedDate());
    }
    @Test
    public void getSize() throws Exception {
        en.setSize(200);
        assertEquals(200, en.getSize());
    }
    @Test
    public void setSize() throws Exception {
        en.setSize(800);
        assertEquals(800, en.getSize());
    }
}
