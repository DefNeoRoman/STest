package production;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Запуск всех тестов сразу через maven
 * для хранения данных используем везде ArrayList
 * так как для работы мне понадобятся методы get и set
 * Относительно LinkedList ArrayList работает с этими методами быстрее
 */
public class Main {
    final static int tpDepth = 4; // Количество нитей в тредпуле
    // 4 ядерный процессор = 4 нити (самый минимум),для того чтобы назначить количество нитей в тредпуле, узнать количество ядер в процессоре
    //есть такой метод, обязательно поискать его.
    // при условии, что в массив аргуемнтов может вводится много путей для поиска
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        PrinterTask pt = new PrinterTask();
        pt.start(); //Здусь запускаем нить которая будет выводить точки и палочки
        List<String> myPaths = new ArrayList<>(); //массив для входных параметров, сюда будут попадать пути до ключа
        List<String> ignor = new ArrayList<>(); //Массив для файлов, которые будем игнорировать,
        // сюда будут попадать пути, которые будт после ключа
        int keyPosition = 0;
        List<Entity> result = new ArrayList<>();// ArrayList для результатов
        for (int i = 0; i < args.length; i++) {//такой цикл for писать нельзя, надо делать через boolean флажок
            if (args[i].contains("-")) {
                keyPosition = i;
                String h = args[i].replaceAll("-", "");//Возможность добавления ключей через еще один if
                //реплэйсить ничего не надо там же в задании просто знак минус, и после него пробел.
                ignor.add(h);
                continue;
            }
            if (keyPosition == 0) {
                myPaths.add(args[i]); // Добавляем пути,
                // предварительно заменив в командной строке вот такие слэши "\" на вот эти "/"
            } else {
                String h = args[i]; // Добавляем файлы, которые будем исключать из поиска
                ignor.add(h);
            }
        }
            ExecutorService service = Executors.newFixedThreadPool(tpDepth); // Реализация многопоточности:
            // был создан тредпул из 4 тредов которым выдавались задачи на выполнение, то есть каждый тред на каждый каталог
        //есть такой ScheduleThreadPool поработать с ним(задачу production.PrinterTask включить в него тоже)
        //отрубать все нити методом shutdown у scheduleThreadPool
            for (String s : myPaths) {
                MyTask t = new MyTask(s, ignor); //Передаем параметры в задачу
                Future<List<Entity>> future = service.submit(t); // Кладем в тредпул
                //Future - это незавершенное задание, подробнее почитать
                List<Entity> le = future.get();
                result.addAll(le);
            }
            FileWriter writer = new FileWriter("result.txt", false);//Лучше сделать через FileOutPutStream там есть буферизация
        //И можно установить параметр кодировки UTF-8, то  есть тот который требуется
            result.parallelStream().forEach(f -> { //
                try {
                    writer.write(f.toString()); // Пишем в файл
                    //Так как строк будет миллиард, то будем использовать временные файлы для записи
                } catch (IOException e) {

                }
            });
        //Сгружать отсортированные записи в отдельный файл, а потом делать сортировку слиянием (merge sort)
            writer.close();//так не пишут - использовать try-with-resources
        pt.interrupt();//прерывание через shutdown
        System.out.println(" Scan operation is completed count files: " + result.size());
            System.exit(0);//По идее этого не требуется, если метод shutdown успешно отработает
    }
}
