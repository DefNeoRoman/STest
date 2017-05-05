package examples;

import production.Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Пользователь on 05.05.2017.
 */
public class EnitityStorage implements Iterator{
    private List<File> listFiles = new ArrayList<>();
    private List<Entity>  currentListEntities = new ArrayList<>(); //сюда попадают листы из файла
    //именно из него возвращается сущность, которая будет попадать в сортировщик

    public EnitityStorage(List<File> listFiles) {
        this.listFiles = listFiles;

    }
    public static Iterator<List<Entity>> work(List<File> lf){
             return new Iterator<List<Entity>>() {
                 @Override
                 public boolean hasNext() {
                     return lf.iterator().next() != null;
                 }

                 @Override
                 public List<Entity> next() {
                     List<Entity> len = new ArrayList<>();
                     try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(lf.iterator().next())))
                     {
                         len =(List<Entity>) ois.readObject();

                     }
                     catch(Exception ex){

                         System.out.println(ex.getMessage());
                     }
                     return len;
                 }
             };
    }
    @Override
    public boolean hasNext() {
        return currentListEntities.iterator().next() != null;
    }

    @Override
    public Object next() {
        return EnitityStorage.work(listFiles).next().iterator().next();
    }
}
