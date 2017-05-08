package production;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;


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
