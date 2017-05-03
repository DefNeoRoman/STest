package util;

import java.io.IOException;
import java.util.List;

/**
 * Created by Пользователь on 03.05.2017.
 */
public interface FileSortStorage <T> extends Iterable<T> {
    public void setObjects(List<T> objects) throws IOException;
}
