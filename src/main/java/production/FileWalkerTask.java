package production;

import util.FileSortStorageObject;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;

public class FileWalkerTask implements Callable<List<FileSortStorageObject>> {
    private String searchPath; //Директория поиска
    private Set<String> ignorList; //Список файлов для игнорирования
    private final int bufferSize = 10000;//Количество записей в буфере(Он же Storage)

    public FileWalkerTask(String searchPath, Set<String> ignorList) {
        this.searchPath = searchPath;
        this.ignorList = ignorList;
    }
    @Override
    public List<FileSortStorageObject> call() throws Exception {
        List<Entity> part = new ArrayList<>();//сам буфер
        List<FileSortStorageObject> resultList = new ArrayList<>(); //здесь храним частицы результата
        Path startPath = FileSystems.getDefault().getPath(searchPath);//Получаем объект Path
        Files.walk(startPath).filter(f -> {
            if (ignorList.contains(f.getFileName().toString())) {
                // Здесь используем Set, так как он с методом contains работает лучше
                return false;
            } else {
                return true;
            }
        }).forEach(f -> { //Для каждого полученного после фильтра файла
            if (part.size() == bufferSize) { //если буфер переполнен
                Collections.sort(part); //Сортируем быстрой сортировкой частицу (как сортировать описано в интерфейсе Comparable)
                try {
                    resultList.add(new FileSortStorageObject(part));//и добавляем в хранилище
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                part.add(new Entity(f.getFileName().toString(), new Date(f.toFile().lastModified()),f.toFile().length()));
            }
        });
        resultList.add(new FileSortStorageObject(part));
        return resultList;
    }
}
