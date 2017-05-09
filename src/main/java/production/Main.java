package production;


import util.FileSort;
import util.FileSortStorageObject;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Запуск всех тестов сразу через maven
 * Перед запуском jar - архива заменить вот эти слэши "\" на вот эти "/"
 * для хранения данных используем везде ArrayList
 * так как для работы мне понадобятся методы get и set
 * Относительно LinkedList, ArrayList работает с этими методами быстрее
 */
public class Main {
    final static int tpDepth = Runtime.getRuntime().availableProcessors(); // Количество нитей в тредпуле
    // получаем количество доступных ядер и реализуем тредпул
    // при условии, что в массив аргументов может вводится много путей для поиска
    public static void main(String[] args) throws InterruptedException, ExecutionException, ClassNotFoundException {
        List<FileSortStorageObject> storage = new ArrayList<>();//Общее хранилище куда заходят частички со всех задач
        PrinterTask pt = new PrinterTask();//Здесь создаем нить которая будет выводить точки и палочки
        List<String> directoryPaths = new ArrayList<>(); //массив для входных параметров, сюда будут попадать пути до ключа
        Set<String> ignor = new HashSet<>(); //Массив для файлов, которые будем игнорировать,
        // сюда будут попадать пути, которые будут после ключа
        // (Выбрали множество HashSet, так как в нем метод contains() работает сразу )
        boolean ignorStart = false;
        for (String arg : args) {
            if (arg.equals("-")) {//Ключи добавлять можно здесь через еще один if
                ignorStart = true;
            }
            if (!ignorStart) {
                directoryPaths.add(arg);

            } else if (ignorStart) {
                ignor.add(arg);
            }
        }
        for (String d : directoryPaths) {
            System.out.println(d);
        }
        ExecutorService service = Executors.newFixedThreadPool(tpDepth); // Реализация многопоточности:
        service.submit(pt); //Запускаем задачу на выполнение
        // был создан тредпул из tpDepth тредов которым выдавались задачи на выполнение, то есть каждый тред на каждый каталог
        //Также выделяются ресурсы для выполнения печатающей задачи
        //метод shutdown() отключит все нити у тредпула

        for (String s : directoryPaths) { //Итерируем по полученным спискам
            FileWalkerTask t = new FileWalkerTask(s, ignor); //Передаем параметры в задачу
            Future<List<FileSortStorageObject>> future = service.submit(t);// результат получаем в виде списка частичек
            //Future - это незавершенное задание, подробнее почитать
            //Положить полученный список в FileStorage
            storage.addAll(future.get());
        }

        FileSort fs = new FileSort();// Инициализируем сортировщик
        for (int i = 0; i < storage.size(); i++) { //Запускаем иетратор по сортировщику
            fs.addPartFiles(storage.get(i)); // , для того чтобы каждая запись доставлась по одному в отсортированном порядке
        }
        File resFile = new File("result.txt");//создаем результирующий файд
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resFile), "UTF8"))) {
            for (Object e : fs) {
                out.append(e.toString());

            }
        } catch (IOException t) {
            t.printStackTrace();
        }
        //записываем в буферизированном потоке получившиеся в файл с кодировкой UTF-8
        service.shutdown();//выключаем тредпул
        System.out.println(" Scan operation is completed");
        System.exit(0);//Завершение программы
    }
}



