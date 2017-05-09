package util;

import org.junit.Test;
import production.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FileSortStorageObjectTest {

    @Test
    public void setObjects() throws Exception {
        List<Entity> le = new ArrayList<>();
        le.add(new Entity("file1", new Date(), 800));
        List<Entity> le2 = new ArrayList<>();
        le2.add(new Entity("file2", new Date(), 400));
        FileSortStorageObject fso = new FileSortStorageObject(le);
        fso.setObjects(le2);
        Entity e = (Entity)fso.iterator().next();
        assertEquals(e.getSize(),le2.get(0).getSize());
    }

}