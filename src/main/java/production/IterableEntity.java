package production;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;

/**
 * Created by Пользователь on 05.05.2017.
 */
public class IterableEntity implements Iterable, Iterator {

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}
