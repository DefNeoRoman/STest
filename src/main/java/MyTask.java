import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Created by Пользователь on 28.04.2017.
 */
public class MyTask implements Callable<List<Entity>> {
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
                return false;
            }
            return true;
        }).forEach(f -> {
            myfiles.add(
                    new Entity(
                            f.getFileName().toString(),
                            new Date(f.toFile().lastModified()),
                            f.toFile().length()));
        });
        List<Entity> res = myfiles.stream().sorted(new EntityComparator()).collect(Collectors.toList());
        //Сортируем по дате, если надо отсортировать в другом порядке, то создаем свой компаратор и в нем  задаем условие сортировки
        return res;
    }
}