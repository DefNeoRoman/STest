package util;

import java.io.IOException;
import java.util.List;

public interface FileSortStorage<T> extends Iterable<T> {
     void setObjects(List<T> objects) throws IOException;
}
