import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Пользователь on 28.04.2017.
 */
public class MyTask implements Callable<List<String>>{
    private String myPath;
    private List<String> ignorList;

    public MyTask(String myPath, List<String> ignorList) {
        this.myPath = myPath;
        this.ignorList = ignorList;
    }

    @Override
    public List<String> call() throws Exception {
        return new ArrayList<>();
    }
}
