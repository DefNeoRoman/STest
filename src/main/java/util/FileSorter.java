package util;

import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Пользователь on 26.04.2017.
 */
public class FileSorter {
    public static List<File> sortByname(List<File> list){
        List sorted;

        sorted = list.stream().sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
        return sorted;
    }
    public static List<File> sortByDate(List<File> list){
        List sorted;
        sorted = list.stream().sorted(Comparator.comparing(o -> new Date(o.lastModified()))).collect(Collectors.toList());
        return sorted;
    }
    public static List<File>sortBySize(List<File> list){
        List sorted;
        sorted = list.stream().sorted(Comparator.comparingLong(File::length)).collect(Collectors.toList());
        return sorted;
    }
}
