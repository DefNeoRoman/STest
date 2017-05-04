package util;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Пользователь on 04.05.2017.
 */
public class TempFileStorage<T> implements Iterable<T> {

    private File file;

    public TempFileStorage(File file) throws IOException{
        this.file = File.createTempFile("FileStorage", "dat");
        file.deleteOnExit();
    }
    public void setObjects(List<T> objects) {
        try(ObjectOutputStream wr = new ObjectOutputStream(new FileOutputStream(file))){

            for (T item : objects) {
                wr.writeObject(item);
            }

        }catch (IOException e){

        }

    }
    /**
     * Итератор по файлу-хранилищу объектов
     */
    public Iterator<T> iterator() {
        try {
            return new Iterator<T>() {
                private ObjectInputStream fr =
                        new ObjectInputStream(new FileInputStream(file));
                T obj;
                public boolean hasNext() {
                    if (obj == null) {
                        try {
                            obj = (T)fr.readObject();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            obj = null;
                        }
                    }
                    return obj != null;
                }
                public T next() {
                    hasNext();
                    T res = obj;
                    obj = null;
                    return res;
                }
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Зачищаем
     */
    protected void finalize() {
        file.delete();
    }
}
