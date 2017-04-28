import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by роман on 27.04.2017.
 */
public class MultiThread {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        walk("D:\\forJava\\examples");
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println("Время выполнения:" + timeConsumedMillis);

    }
    public static void walk(String pathName) throws IOException{
        List<Entity> myfiles =
                new ArrayList<>();
        Path startPath = FileSystems.getDefault().getPath(pathName);
        Files.walk(startPath).parallel().filter(f->{
            if ("executors".equals(f.getFileName().toString())
                    || "kalugaTest".equals(f.getFileName().toString())){

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
        System.out.println("найдено файлов: " + myfiles.size());

    }


}


