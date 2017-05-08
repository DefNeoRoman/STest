package examples;

import production.Entity;
import util.FileSort;
import util.FileSortStorageObject;

import java.io.*;
import java.util.*;

public class MergeSortExample {

    public static void main(String[] args) throws IOException {
//теперь возьмем три файла, которые положим в лист, результатом работы должен получиться один файл
        List<File> lf = new ArrayList<>();
        List<Entity> subList = new ArrayList<>();
        List<FileSortStorageObject> lfso = new ArrayList<>();
        for (int i = 0; i<300; i++){

            if(subList.size() == 30){
                Collections.sort(subList);
                FileSortStorageObject fso = new FileSortStorageObject(subList);
                lfso.add(fso);
                subList.clear(); // очищение буферного листа

            }else {
                subList.add(new Entity("asd"+i, new Date(), new Random().nextInt(2000)));
            }

        }

        FileSort g = new FileSort(subList.iterator());
        for(FileSortStorageObject fo : lfso){
            g.addPartFiles(fo);
        }


        for (Object r: g){
            System.out.println(r.toString());
        }


    }



    public static List<Entity> deserialAndSort(File f){

        List<Entity> deserialList = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
        {
            while(true){
                deserialList.add((Entity)ois.readObject());
            }

        }
        catch(Exception ex){


        }

        return sort(deserialList);

    }
    public static List<Entity> sort(List <Entity> len){

        if(len.size() < 2) return len;
        int m = len.size() / 2;
        List<Entity> arr1 = len.subList(0,m);
        List<Entity> arr2 = len.subList(m,len.size());
        return merge(sort(arr1), sort(arr2));
    }
    //слияние двух массивов в один отсортированный
    public static List<Entity> merge(List<Entity> arr1,List<Entity> arr2){
        int n = arr1.size() + arr2.size(); //размер буфера
        List<Entity> arr = new ArrayList<>(n);
        int i1=0;
        int i2=0;
        for(int i=0; i<n; i++){
            if(i1 == arr1.size()){
                arr.add(i,arr2.get(i2++));
            }else if(i2 == arr2.size()){
                arr.add(i,arr1.get(i1++));
            }else{
                if(arr1.get(i1).getSize() < arr2.get(i2).getSize()){
                    arr.add(i,arr1.get(i1++));
                }else{
                    arr.add(i,arr2.get(i2++));
                }
            }
        }
        return arr;
    }
}
