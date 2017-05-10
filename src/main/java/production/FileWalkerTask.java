package production;

import util.FileSortStorageObject;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;

public class FileWalkerTask implements Callable<List<FileSortStorageObject>> {
    private String searchPath;                    //Директория поиска
    private Set<String> ignoreList;               //Список файлов для игнорирования исправить ignore
    private static final int BUFFER_SIZE = 1000;  //Количество записей в буфере(Он же Storage) большими буквами констаета в одно месте

    public FileWalkerTask(String searchPath, Set<String> ignoreList) {
        this.searchPath = searchPath;
        this.ignoreList = ignoreList;
    }
    @Override
    public List<FileSortStorageObject> call() throws Exception {
        List<Entity> part = new ArrayList<>();//сам буфер
        List<FileSortStorageObject> resultList = new ArrayList<>(); //здесь храним части результата
        Path startPath = Paths.get(searchPath);//Получаем объект Path
        Files.walk(startPath).filter(f -> !ignoreList.contains(f.getFileName().toString()))
                .forEach(f -> { //Для каждого полученного после фильтра файла
            if (part.size() == BUFFER_SIZE) { //если буфер переполнен
                Collections.sort(part); //Сортируем быстрой сортировкой часть
                try {
                    resultList.add(new FileSortStorageObject(part));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                part.add(new Entity(f.getFileName().toString(), new Date(f.toFile().lastModified()),f.toFile().length()));
            }
        });
        resultList.add(new FileSortStorageObject(part));//незавершенная часть
        return resultList;
    }
}
