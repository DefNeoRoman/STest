package example;

import production.Entity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Пользователь on 03.05.2017.
 */
public class SerialTest {
    public static void main(String[] args) {
        try {
             Entity en = new Entity("Moscow", new Date(),
                    4000);
             FileOutputStream fos = new FileOutputStream("entity.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(en);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerialTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerialTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }
}
