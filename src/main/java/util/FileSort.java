package util;

import java.io.IOException;
import java.util.*;

public class FileSort<T extends Comparable<T>> implements Iterable<T> {

    //это класс сортировщик, который рабооатет со всеми объектами, котрые реализуют интерфейс Comparable
    //то есть все что помещаетсz в этот сортировщик должно расширяться от Comparble.
    //алгоритм сортировки можно задать свой в compareTo (Той сущности, которую кладем в него)
    //тогда метод сорт будет сортировать как надо, а сортировщик будет доставать с помощью итератора наименьший
    private int bufferSize = 10000; //Размер буфера
    private List<FileSortStorage> partFiles = new LinkedList<FileSortStorage>(); //Частицы файлов
    private Iterator<T> source;// Инициализирующий итератор
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
    public void addPartFiles(FileSortStorageObject fso) {
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
        Collections.sort(part);//сортировка
    }

    /**
     * Получение результата в виде итератора
     */
    public Iterator<T> iterator() {

        if (source == null) { //Для того чтобы запустить сортировщик с пустым конструктором
            setSource(partFiles.get(0).iterator()); // Устанавливаем источником самую первую частицу
            partFiles.remove(partFiles.get(0));//затем ее соазу удаляем, чтобы ее не было в блоке динамической инициализации
        }

        if (partFiles.size() == 0) {
            // оптимизация, если всё уместилось в память
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
                // (так как буфер переполняется невсегда) из незавершенной части тоже получаем итератор и кладем в список итераторов
                for (FileSortStorage f : partFiles) {
                    iterators.add(f.iterator());//здесь кладем наполненные итераторы
                }
                for (Iterator<T> item : iterators) {
                    if (item.hasNext()) {
                        items.add(item.next());
                    } else {
                        throw new RuntimeException("failed to get first for iterator");
                        //Обработка исключительной ситуации, если нету самого первого итератора
                        }
                }
            }

            /**
             * Находит среди объектов минимальный, возвращает доступность очередного объекта
             */
            public boolean hasNext() {
                if (minIdx == null) {
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i) != null && (minIdx == null || items.get(i).compareTo(items.get(minIdx)) < 0))
                        {
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
//Этот метод не реализуется нигде, всегда нужно выбрасывать это исключение
            }
        };
    }

    /**
     * Производит чтение исходных данных с сохранением блоков во временные файлы
     */
    void sortParts() throws IOException {
        while (source.hasNext()) { // source - это Iterator
            part.add((T) source.next()); // part - это LinkedList<T>
            if (part.size() >= bufferSize && source.hasNext()) {
                Collections.sort(part);
                partFiles.add(new FileSortStorageObject(part));
                part.clear();
            }
        }
    }
}





