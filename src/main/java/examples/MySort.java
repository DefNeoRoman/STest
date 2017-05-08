package examples;

import production.Entity;
import util.FileSortStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MySort implements Iterable {
    private Iterator<File> source;
    private int bufferSize = 100;
    private List<FileSortStorage> partFiles = new LinkedList<FileSortStorage>();
    private List<Entity> part = new LinkedList<Entity>();
    public MySort() {
    }

    public MySort(Iterator<File> source) {
        this.source = source;
    }

    @Override
    public Iterator iterator() {
        List<Entity> items = new ArrayList<>();
        List<Iterator<Entity>> iterators = new ArrayList<>();
        Integer minIndex = null;
        {
            iterators.add(part.iterator());

        }
        return new Iterator() {
            @Override
            public boolean hasNext() {

                return true;
            }

            @Override
            public Entity next() {
                int i = 1;
                return new Entity();
            }
        };
    }
}
