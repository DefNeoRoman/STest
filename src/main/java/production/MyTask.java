package production;//Как угодно может называться только не production.MyTask(FileWalkerTask например)
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

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

        Files.walk(startPath).filter(f -> {  //если добавить .parallel(), то ничего не произойдет
            if (ignorList.contains(f.getFileName().toString())// Здесь нужно 
               // использовать Set, так как он с методом contains работает сразу Set <production.Entity>
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
        List<Entity> res = myfiles.stream().sorted(new EntityComparator()).collect(Collectors.toList());//Сортировку делать сразу,
        //не надо создавать отдельный лист результатов (через лямбда - выражеие сделать компаратор)
        //Сортируем по дате, если надо отсортировать в другом порядке, то создаем свой компаратор и в нем  задаем условие сортировки
        return res;
    }
}
