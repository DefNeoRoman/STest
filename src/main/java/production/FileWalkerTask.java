package production;//Как угодно может называться только не production.FileWalkerTask(FileWalkerTask например)
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class FileWalkerTask implements Callable<List<File>> {
    private String myPath;
    private Set<String> ignorList;
    private int key;
    private final int bufferSize = 50;//Количество записей в буфере
    public FileWalkerTask(String myPath, Set<String> ignorList, int key) {
        this.myPath = myPath;
        this.ignorList = ignorList;
        this.key = key;
    }
    @Override
    public List<File> call() throws Exception {

        List<Entity> part = new ArrayList<>();//сам буфер
        Path startPath = FileSystems.getDefault().getPath(myPath);
        List<File> lf = new ArrayList<>(); //здесь будем хранить ссылки на файлы


        Files.walk(startPath).filter(f -> {  //если добавить .parallel(), то ничего не произойдет
            if (ignorList.contains(f.getFileName().toString())// Здесь нужно 
               // использовать Set, так как он с методом contains работает сразу Set <production.Entity>
                    ) {
                return false;
            }
            return true;
        }).forEach(f -> {

                if(part.size()== bufferSize){
                    try{
                        File file = File.createTempFile("FileStorage"+key+f.hashCode(), "dat");
                        try(ObjectOutputStream wr = new ObjectOutputStream(new FileOutputStream(file))){

                              wr.writeObject(part);
                              lf.add(file);
                              key = key*key+9;
                            System.out.println("50 files was written");
                            part.clear();
                        }catch(IOException e){

                        }
                    }catch(IOException e){

                    }


            } else{
                    part.add(
                            new Entity(
                                    f.getFileName().toString(),
                                    new Date(f.toFile().lastModified()),
                                    f.toFile().length()));
                }



        });
        File endFile = File.createTempFile("endFile"+key+part.hashCode(),"dat");
        ObjectOutputStream wr = new ObjectOutputStream(new FileOutputStream(endFile));
        wr.writeObject(part);//Дописываем завершающую часть
        lf.add(endFile);
        System.out.println("endFile written");
       //Сортировку делать сразу,
        //не надо создавать отдельный лист результатов (через лямбда - выражеие сделать компаратор)
        //Сортируем по дате, если надо отсортировать в другом порядке, то создаем свой компаратор и в нем  задаем условие сортировки

        return lf;
    }
}
