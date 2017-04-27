import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by роман on 27.04.2017.
 */
public class MultiThread {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

       walk("D:\\forJava");
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println("Программа выполнена за: " + timeConsumedMillis);
    }
    public static void walk(String pathName) throws IOException{

        Path startPath = FileSystems.getDefault().getPath(pathName);
        Files.walk(startPath)
                .filter( path -> path.toFile().isFile())
                .filter( path -> path.toString().endsWith(".docx"))
                .forEach( System.out::println );

    }

}


