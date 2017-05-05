package examples;

import production.Entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Пользователь on 05.05.2017.
 */
public class App {
    //Сюда попадает список временных файлов
    //в каждом временном файле лежит список по 50 записей
    //
    public static void main(String[] args) {
        List<Entity> len1 = new ArrayList<>();
        List<Entity> len2 = new ArrayList<>();
        List<Entity> len3 = new ArrayList<>();
        for(int i = 0; i<90; i++){
            Entity en = new Entity("asdfg"+new Random(i).nextInt()*i,new Date(),200*i);
            if(i<30){
                len1.add(en);
            }else if(i>30 && i<60){
                len2.add(en);
            }else if (i>60 && i<90){
                len3.add(en);
            }
        }
        List<File> newLf = new ArrayList<>();

        for(int i=0; i<10; i++){

        }
        List<List<Entity>> listOfList = new ArrayList<>();
        listOfList.add(len1);
        listOfList.add(len2);
        listOfList.add(len3);


        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.dat")))
        {
            
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        listOfList.stream().forEach(listO -> {
            System.out.println("количесвто записей"+listO.size());

        });


    }
}
