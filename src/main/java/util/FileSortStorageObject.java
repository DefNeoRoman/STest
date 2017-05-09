package util;

import java.io.*;
import java.util.Iterator;
import java.util.List;
//Частица результата
public class FileSortStorageObject<T> implements FileSortStorage<T> {
    private final File file;
    /**
     * Конструктор, создаёт временный файл и сохраняет в него объекты
     */
    public FileSortStorageObject(List<T> objects) throws IOException {
        file = File.createTempFile("FileSort", "dat");
        file.deleteOnExit();
        setObjects(objects);
    }
    /**
     * Сохраняем объекты в файл
     */
    public void setObjects(List<T> objects){
        try(ObjectOutputStream wr = new ObjectOutputStream(new FileOutputStream(file)) ) {
            for (T item : objects) {
                wr.writeObject(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
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


