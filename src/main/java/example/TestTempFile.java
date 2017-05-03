package example;

import util.FileSort;

import java.util.Iterator;
import java.util.Random;

public class TestTempFile {
    public static void main(String[] args) {
        System.out.println("Test start");
        // создаём класс-сортировщик
        FileSort<Double> sort = new FileSort<>(
                // в конструктор передаём итератор - источник данных
                // у нас он просто генерирует случайные числа
                new Iterator<Double>() {
                    private int i = 0;
                    private Random rand = new Random();

                    public boolean hasNext() {
                        if (i >= 1000) {
                            System.out.println("generator finish");
                        }
                        return i < 1000;
                    }

                    public Double next() {
                        i++;
                        return rand.nextDouble();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                });
        int i = 0;
        // выводим отсортированный результат
        for (Object res : sort) {
            if (++i % 10000 == 0) {
                // когда результатов много имеет смысл их вывод отключить
                // и просто считать количество
                System.out.println(i);
            }
            System.out.println(" == " + res);
        }
    }
}


