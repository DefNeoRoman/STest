package example;

import production.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Пользователь on 03.05.2017.
 */
public class DeserialTest {
    public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream("entity.ser")){
            ObjectInputStream ois = new ObjectInputStream(fin);

            List<Entity> len = (List<Entity>) ois.readObject();
            System.out.println(len.toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DeserialTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DeserialTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }


}
