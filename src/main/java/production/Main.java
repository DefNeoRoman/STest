package production;
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
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        PrinterTask pt = new PrinterTask();//Здесь создаем нить которая будет выводить точки и палочки
        List<String> directoryPaths = new ArrayList<>(); //массив для входных параметров, сюда будут попадать пути до ключа
        Set<String> ignor = new HashSet<>(); //Массив для файлов, которые будем игнорировать,
        // сюда будут попадать пути, которые будут после ключа
        // (Выбрали множество HashSet, так как в нем метод contains() работает сразу )
        boolean ignorStart = false;
        List<File> partFiles = new ArrayList<>();// ArrayList для результатов


        for (String arg: args) {
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
                Future<List<File>> future = service.submit(t); // Кладем в тредпул
                //Future - это незавершенное задание, подробнее почитать
                //Положить полученный список в FileStorage
                System.out.println("ready to Get Future");
                List<File> le = future.get();
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
    public static void sortAndWrite(List<File> lf) throws IOException{
            Entity currentEntity = new Entity();

            for (File f: lf){

                try (FileInputStream fin = new FileInputStream(f)){
                    ObjectInputStream ois = new ObjectInputStream(fin);

                    TreeSet<Entity> len = (TreeSet<Entity>) ois.readObject();

                    Entity enToRemove = len.stream().min(Entity::compareTo).get();
                    if(currentEntity.compareTo(enToRemove) > 0 ){
                        currentEntity = enToRemove;
                        len.remove(enToRemove);

                    }
                } catch(Exception g){

                }
            }

              writer.write(currentEntity.toString());


    }
}
