package example;

import production.Entity;
import production.EntityComparator;
import util.FileSort;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Пользователь on 03.05.2017.
 */
public class SerialWithEntity {
    public static void main(String[] args) throws IOException{

        List<Entity> myfiles = new ArrayList<>();
        Path startPath = FileSystems.getDefault().getPath("E:\\Roman\\Извещения");

        Files.walk(startPath).forEach(f -> {
            myfiles.add(
                    new Entity(
                            f.getFileName().toString(),
                            new Date(f.toFile().lastModified()),
                            f.toFile().length()));
        });
        List<Entity> res = myfiles.stream().sorted(new EntityComparator()).collect(Collectors.toList());
        FileSort<Entity> sort = new FileSort<Entity>(

                new Iterator<Entity>() {
                    private int i = 0;
                   List<Entity> le = res;

                    public boolean hasNext() {
                        if (i >= 1000) {
                            System.out.println("generator finish");
                        }
                        return i < 1000;
                    }

                    public Entity next() {
                        i++;
                        return le.get(i);
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                });
        int i = 0;
        // выводим отсортированный результат
        for (Object result : sort) {
            if (++i % 10000 == 0) {
                // когда результатов много имеет смысл их вывод отключить
                // и просто считать количество
                System.out.println(i);
            }
            System.out.println(" == " + res);
        }
    }
}
