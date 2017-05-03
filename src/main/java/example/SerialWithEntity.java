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
        Path startPath = FileSystems.getDefault().getPath("E:\\Roman\\");

        Files.walk(startPath).forEach(f -> {
            myfiles.add(
                    new Entity(
                            f.getFileName().toString(),
                            new Date(f.toFile().lastModified()),
                            f.toFile().length()));
        });
        List<Entity> res = myfiles.stream().sorted(new EntityComparator()).collect(Collectors.toList());


        FileSort<Entity> sort = new FileSort<Entity>(res.iterator());


        // выводим отсортированный результат
        for (Object result : sort) {

            System.out.println(" == " + result);
        }

    }
}
