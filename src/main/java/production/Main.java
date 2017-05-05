package production;
import util.FileSort;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Запуск всех тестов сразу через maven
 * для хранения данных используем везде ArrayList
 * так как для работы мне понадобятся методы get и set
 * Относительно LinkedList ArrayList работает с этими методами быстрее
 * В самом начале создать FileStorage,
 * после чего пройтись по нему итератором, так как в нем лежат сериализуемые объекты
 */
public class Main {
    final static int tpDepth = Runtime.getRuntime().availableProcessors(); // Количество нитей в тредпуле
    private static FileWriter writer;
    // получаем количество доступных ядер и реализуем тредпул
   // при условии, что в массив аргументов может вводится много путей для поиска
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {
        String[] argt = new String[3];
        argt[0] = "E:\\Roman";
        argt[1] = "-";
        argt[2] = "E:\\Roman\\DontTouchThis";
        Set<IterableEntity> partFiles = new TreeSet<>();// ArrayList для результатов

        PrinterTask pt = new PrinterTask();//Здесь создаем нить которая будет выводить точки и палочки
        List<String> directoryPaths = new ArrayList<>(); //массив для входных параметров, сюда будут попадать пути до ключа
        Set<String> ignor = new HashSet<>(); //Массив для файлов, которые будем игнорировать,
        // сюда будут попадать пути, которые будут после ключа
        // (Выбрали множество HashSet, так как в нем метод contains() работает сразу )
        boolean ignorStart = false;



        for (String arg: argt) {
            if (arg.equals("-")) {
                ignorStart = true;
            }
            if(!ignorStart){
                directoryPaths.add(arg);

            } else if(ignorStart){
                ignor.add(arg);
            }


        }
        for(String d : directoryPaths){
            System.out.println(d);
        }

            ExecutorService service = Executors.newFixedThreadPool(tpDepth); // Реализация многопоточности:
            service.submit(pt); //Запускаем задачу на выполнение
            // был создан тредпул из tpDepth тредов которым выдавались задачи на выполнение, то есть каждый тред на каждый каталог
        //Также выделяются ресурсы для выполнения печатающей задачи
       //отрубать все нити методом shutdown у scheduleThreadPool
            int count = 1; // счетчик чтобы файлы Storage не спутались

        for (String s : directoryPaths) {
            FileWalkerTask t = new FileWalkerTask(s, ignor,count); //Передаем параметры в задачу
            count++;
            //random - это рандомный номер файла
            Future<List<IterableEntity>> future = service.submit(t); // Кладем в тредпул
            //Future - это незавершенное задание, подробнее почитать
            //Положить полученный список в FileStorage
            System.out.println("ready to Get Future");
            List<IterableEntity> le = future.get();
            partFiles.addAll(le);

        }

        //Лучше сделать через FileOutPutStream там есть буферизация
        //И можно установить параметр кодировки UTF-8, то  есть тот который требуется
        //здесь достать объекты из FileStorage сделать сортировку слиянием, далее записать их в конечный файл

        //Сгружать отсортированные записи в отдельный файл, а потом делать сортировку слиянием (merge sort)
        writer = new FileWriter("result.txt", false);
        sortAndWrite(partFiles);
        service.shutdown();
        System.out.println(" Scan operation is completed");
        System.exit(0);//По идее этого не требуется, если метод shutdown успешно отработает
    }
    public static void sortAndWrite(Set<IterableEntity> lf) throws IOException, ClassNotFoundException{

        FileSort<Entity> fsen = new FileSort<Entity>(lf.iterator().next().iterator());
        //Лист из файлов
        // -> нужно получить файл -> десериализовать из
        // него List<Entity>
        // -> из него получить Entity и передать в сортировщик
        fsen.forEach(entity -> {
            try {
                writer.write(entity.toString());
            }catch(Exception e){

            }

        });

    }
}



