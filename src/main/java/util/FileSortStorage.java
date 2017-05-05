package util;

import java.io.IOException;
import java.util.List;

public interface FileSortStorage<T> extends Iterable<T> {
    public void setObjects(List<T> objects) throws IOException;
}
