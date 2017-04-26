package util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Пользователь on 26.04.2017.
 */
public class FileExcluder implements FilenameFilter {
    private final String extension;

    public FileExcluder(String ext) {
        extension = ext;
    }


    public boolean accept(File dir, String name) {
        return !(name.endsWith(extension));
    }
}
