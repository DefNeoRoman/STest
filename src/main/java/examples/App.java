package examples;

import production.Entity;
import util.FileSort;

import java.io.*;
import java.util.*;

/**
 * Created by Пользователь on 05.05.2017.
 */
public class App {

    public static void main(String[] args) {
     Entity en1 = new Entity("fgh", new Date(), 560);
        Entity en2 = new Entity("ghj", new Date(), 700);

        List<Entity> len = new ArrayList<>();
        len.add(en1);
        len.add(en2);
        Entity result = len.stream().min((o1, o2) -> {
            return (int)(o1.getSize() - o2.getSize());
        }).get();
        System.out.println(result.toString());
    }
}
