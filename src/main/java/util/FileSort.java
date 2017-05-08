package util;

import java.io.IOException;
import java.util.*;

public class FileSort<T extends Comparable<T>> implements Iterable<T> {
    //то есть все что помещаетс в этот сортировщик должно расширяться от Comparble.
    //алгоритм сортировки можно задать свой в compareTo
    //тогда метод сорт будет сортировать как надо
    private int bufferSize = 10000; //Размер буфера
    //будет высший пилотаж, если мы получим программно количество оперативной памяти
    private List<FileSortStorage> partFiles = new LinkedList<FileSortStorage>(); //Части файлов Получается так?
    private Iterator<T> source;// Какой-то итератор
    private List<T> part = new LinkedList<T>();//Часть которая сохраняется



    /**
     * Конструктор по умолчанию, ничего не делает
     */
    public FileSort() {
    }
    /**
     * Конструктор с параметром - источником
     */
    public FileSort(Iterator<T> newSource) { //Тип должен быть итератор
        setSource(newSource);
        //принимает итератор на вход идем в метод setsource
    }
    /**
     * Конструктор с двумя параметрами - источником и количеством объектов на файл
     */
    public void addPartFiles(FileSortStorageObject fso){

        partFiles.add(fso);
    }
    public FileSort(Iterator<T> newSource, Integer newSize) {
        this(newSource);
        setBufferSize(newSize);
    }
    /**
     * Установка количества объектов на один файл
     */
    public void setBufferSize(int newSize) {
        bufferSize = newSize;
    }
    /**
     * Установка источника данных, используется итератор
     */
    public void setSource(Iterator<T> newSource) {
        source = newSource;//по идее должно быть this.source
        try {
            sortParts(); //идем в метод sortparts
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(part);
    }
    /**
     * Получение результата в виде итератора
     */
    public Iterator<T> iterator() {
        if (partFiles.size() == 0) {
            // маленькая оптимизация, если всё уместилось в память
            return part.iterator();
        }
        return new Iterator<T>() {
            Long t = 0L;
            List<T> items = new ArrayList<T>();
            List<Iterator<T>> iterators = new ArrayList<Iterator<T>>();
            Integer minIdx = null;
            // динамическая инициализация итератора, вместо конструктора
            {
                iterators.add(part.iterator());// всегда остается незавершенная часть
                // (так как буфер переполняется невсегда)
                for (FileSortStorage f : partFiles) {
                    iterators.add(f.iterator());
                }
                for (Iterator<T> item : iterators) {
                    if (item.hasNext()) {
                        items.add(item.next());
                    } else {
                        throw new RuntimeException("failed to get first for iterator");
                    }
                }
            }
            /**
             * Находит среди объектов минимальный, возвращает доступность очередного объекта
             */
            public boolean hasNext() {
                if (minIdx == null) {
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i) != null &&(minIdx == null

                                || items.get(i).compareTo(items.get(minIdx)) < 0)) {
                            minIdx = i;
                        }
                    }
                }
                return minIdx != null;
            }
            /**
             * Если есть доступный объект - возвращает,
             * замещая его в доступных на очередной из файла
             */
            public T next() {
                T res = null;
                if (hasNext()) {
                    res = items.get(minIdx);
                    if (iterators.get(minIdx).hasNext()) {
                        items.set(minIdx, iterators.get(minIdx).next());
                        //почему так происходит? А потому что все находящееся в итераторс уже отсортирвоано
                        //так как сортируемое наследовано от Comparable.
                    } else {
                        items.set(minIdx, null);
                    }
                }
                minIdx = null;
                return res;
            }
            public void remove() {
                throw new UnsupportedOperationException();
//Этот метод не реализуется нигде
            }
        };
    }

    /**
     * Производит чтение исходных данных с сохранением блоков во временные файлы
     */
    void sortParts() throws IOException {
        while (source.hasNext()) { // source - это Iterator
            part.add((T)source.next()); // part - это LinkedList<T>
            if (part.size() >= bufferSize && source.hasNext()) {
                Collections.sort(part);
                partFiles.add(new FileSortStorageObject(part));
                part.clear();
            }
        }
    }
}





