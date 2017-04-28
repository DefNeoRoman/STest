import java.io.*;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Пользователь on 26.04.2017.
 */
public class Main {


    final static int tpDepth = 4; // Количество нитей в тредпуле
    // 4 ядерный процессор = 4 нити (самый минимум),
    // при условии, что в массив аргуемнтов может вводится много путей для поиска


    String key;//Здесь будет сам ключ

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        List<String> myPaths = new ArrayList<>(); //массив для входных параметров, сюда будут попадать пути до ключа
        List<String> ignor = new ArrayList<>(); //Массив для полных путей, которые будем игнорировать,
        // сюда будут попадать пути, которые будт после ключа
        int keyPosition =0;
        List<Entity> result = new ArrayList<>();

        for (int i = 0; i < args.length; i++){


            if (keyPosition == 0){
                myPaths.add(args[i]);
            }else {
                String h = args[i].replaceAll("-", "");
                ignor.add(h);
                System.out.println(h);
            }

            if(args[i].contains("-")){
                keyPosition = i;
            }



        }
        long start = System.currentTimeMillis();
        ExecutorService service =  Executors.newFixedThreadPool(tpDepth);
        for (String s: myPaths){
            MyTask t = new MyTask(s,ignor);
            Future<List<Entity>> future = service.submit(t);
            List<Entity> le = future.get();
            result.addAll(le);
        }


        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println("найдено файлов:" + result.size());
        result.forEach(System.out::println);
        System.out.println("Время выполнения:" + timeConsumedMillis);


    }


}
