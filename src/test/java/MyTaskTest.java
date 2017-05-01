import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.junit.Assert.*;

public class MyTaskTest {
    private static ExecutorService service;
    private String myTestPath;
    private List<String> myIgnorTest = new ArrayList<>();
    MyTask t;
    @Before
    public void setUp() throws Exception {
        service = Executors.newFixedThreadPool(4);
        File f = new File("file1.txt");
        FileWriter writer = new FileWriter(f);
        writer.write("hello");
        myTestPath = f.getAbsolutePath();
        myIgnorTest.add("file1.txt");
        t = new MyTask(myTestPath, myIgnorTest);
    }
    @Test
    public void call() throws Exception {
        Future<List<Entity>> future = service.submit(t);
        List<Entity> le = future.get();
        List<Entity> emptyList = new ArrayList<>();
        assertEquals(emptyList, le);
    }
}