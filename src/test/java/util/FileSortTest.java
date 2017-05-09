package util;

import org.junit.Test;
import production.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FileSortTest {
    private FileSort fs = new FileSort();
    private List<Entity> le = new ArrayList<>();
    @Test
    public void addPartFiles() throws Exception {

        le.add(new Entity("filename", new Date(), 4400));
        FileSortStorageObject fso = new FileSortStorageObject(le);
        fs.addPartFiles(fso);
        assertEquals(fs.iterator().hasNext(), true);
    }
    @Test
    public void setSource() throws Exception {
        le.add(new Entity("filename", new Date(), 4400));
        fs.setSource(le.iterator());
        assertEquals(fs.iterator().hasNext(),true);
    }
}