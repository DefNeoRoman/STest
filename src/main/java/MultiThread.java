import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by роман on 27.04.2017.
 */
public class MultiThread {
    final static int tpDepth = 4; // Количество нитей в тредпуле
    // 4 ядерный процессор = 4 нити (самый минимум),
    // при условии, что в массив аргуемнтов может вводится много путей для поиска
   //массив для входных параметров, сюда будут попадать пути до ключа
   //Масси для полных путей, которые будем игнорировать,
    // сюда будут попадать пути, которые будт после ключа
    String key;//Здесь будет сам ключ

    public static void main(String[] args) throws IOException {
        List<String> myPaths = new ArrayList<>();
        List<String> ignor = new ArrayList<>();
        int keyPosition =0;

        for (int i = 0; i < args.length; i++){


           if (keyPosition == 0){
               myPaths.add(args[i]);
           }else {
               ignor.add(args[i].replaceAll("-",""));
           }

            if(args[i].contains("-")){
                keyPosition = i;
            }



        }
        ExecutorService service =  Executors.newFixedThreadPool(tpDepth);
        for (String s: myPaths){
            MyTask t = new MyTask(s,ignor);
            Future<List<String>> future = service.submit(t);
        }
        long start = System.currentTimeMillis();
        walk("D:\\forJava\\examples");
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println("Время выполнения:" + timeConsumedMillis);

    }

    public static void walk(String pathName) throws IOException {

        List<Entity> myfiles =
                new ArrayList<>();
        Path startPath = FileSystems.getDefault().getPath(pathName);
        Files.walk(startPath).parallel().filter(f -> { //Многопоточность реализована в Java 8
            if ("executors".equals(f.getFileName().toString())
             || "kalugaTest".equals(f.getFileName().toString())) {
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


