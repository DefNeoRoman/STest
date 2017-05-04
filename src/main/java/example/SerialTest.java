package example;

import production.Entity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Пользователь on 03.05.2017.
 */
public class SerialTest {
    public static void main(String[] args) {
        try {
             Entity en = new Entity("Moscow", new Date(),4000);
             Entity en1 = new Entity("Piter", new Date(),4050);
             Entity en2 = new Entity("Kaluga", new Date(),4060);
             Entity en3 = new Entity("Tula", new Date(),4800);
            List<Entity> le = new ArrayList<>();
            le.add(en);
            le.add(en1);
            le.add(en2);
            le.add(en3);

             FileOutputStream fos = new FileOutputStream("entity.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(le);


        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerialTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerialTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }
}
