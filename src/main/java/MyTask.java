import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Пользователь on 28.04.2017.
 */
public class MyTask implements Callable<List<Entity>>{
    private String myPath;
    private List<String> ignorList;

    public MyTask(String myPath, List<String> ignorList) {
        this.myPath = myPath;
        this.ignorList = ignorList;
    }

    @Override
    public List<Entity> call() throws Exception {
        List<Entity> myfiles = new ArrayList<>();
        Path startPath = FileSystems.getDefault().getPath(myPath);
        Files.walk(startPath).parallel().filter(f -> { //Многопоточность реализована в Java 8
            if (ignorList.contains(f.getFileName().toString())
                   ) {
                return true;
            }
            return false;
        }).forEach(f -> {
            myfiles.add(
                    new Entity(
                            f.getFileName().toString(),
                            new Date(f.getFileName().toFile().lastModified()),
                            f.getFileName().toFile().length()));
        });
        myfiles.stream().sorted(new EntityComparator()).forEach(System.out::println);
        return myfiles;
    }
}
