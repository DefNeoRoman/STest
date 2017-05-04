import org.junit.Before;
import org.junit.Test;
import production.Entity;
import production.FileWalkerTask;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.junit.Assert.*;

public class FileWalkerTaskTest {
    private static ExecutorService service;
    private String myTestPath;
    private List<String> myIgnorTest = new ArrayList<>();
    FileWalkerTask t;
    @Before
    public void setUp() throws Exception {


    }
    @Test
    public void call() throws Exception {

    }
}
