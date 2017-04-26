import util.FileExcluder;
import util.FileSorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Пользователь on 26.04.2017.
 */
public class TextHandler {
    private String directoryName;
    private String filesIgnored;
    private String sortParam;

    private List<File> currentList;
    private long filesNumber;
    private  long directoriesNumber;
    private  long totalLength;



    public long getFilesNumber() {
        return filesNumber;
    }

    public void setFilesNumber(long filesNumber) {
        this.filesNumber = filesNumber;
    }

    public long getDirectoriesNumber() {
        return directoriesNumber;
    }

    public void setDirectoriesNumber(long directoriesNumber) {
        this.directoriesNumber = directoriesNumber;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public void setCurrentList(List<File> currentList) {
        this.currentList = currentList;
    }

    public List<File> getCurrentList() {
        return currentList;
    }


    public TextHandler() {
    }

    public TextHandler(String directoryName, String filesIgnored, String sortParam) {

        this.directoryName = directoryName;
        this.filesIgnored = filesIgnored;
        this.sortParam = sortParam;
    }



    public void run() {

        File searchDirectory = new File(directoryName);


        List<File>nonSortRes = createWorkList(searchDirectory,filesIgnored);
        List<File>countedRes = find(nonSortRes);
        List<File> sortedRes = doSort(countedRes, sortParam);
        setCurrentList(sortedRes);

    }
    public List<File> find(List<File> lf){
        List  result = new ArrayList();
        try {
            if(lf == null) {
                throw new Exception("Ошибка: не заданы параметры поиска");
            }


            filesNumber = 0;
            directoriesNumber = 0;
            totalLength = 0;

            // опять кладем в массив

            //выполняем поиск
            countFiles(lf,result);

        }catch (Exception e){

        }

        return result;

    }

    public List countFiles(List<File> lf, List<File> res){
        try {

            //обнуляем все счетчики

            for(int i = 0; i < lf.size(); i++) {
                //если это директория (папка)...
                if(lf.get(i).isDirectory()) {
                    directoriesNumber++;
                    res.add(lf.get(i));
                    List<File> newList = new ArrayList<>();
                    File [] mFile = lf.get(i).listFiles();
                    for (File file: mFile){
                        newList.add(file);
                    }
                    countFiles(newList,res);
                    //выполняем поиск во вложенных директориях
                }
                //если это файл
                else {
                    //...добавляем текущий объект в список результатов,
                    //и обновляем значения счетчиков
                    filesNumber++;
                    totalLength += lf.get(i).length();
                    res.add(lf.get(i));
                }
            }
        }
        catch(Exception err) {
            System.out.println(err.getMessage());
        }
        return res;
    }

    public static void main(String[] args)throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String param = br.readLine();

        if(param.isEmpty()){
            System.out.println("ecnm");
        }

        List<File> test = new ArrayList<>();
        TextHandler th = new TextHandler();
        th.doSort(test,param);


        System.out.println(param);


    }
    public List<File> createWorkList(File searchDirectory, String filesIgnored ){

        List <File> res = new ArrayList<>();

        try{

            if(filesIgnored == ""){
                System.out.println("Ветка default");
                File[] wList = searchDirectory.listFiles();
                //Положили в коллекцию
                for(int i=0; i<wList.length; i++){
                    res.add(wList[i]);
                }


            } else if(checkWithRegExp("-db",filesIgnored)){
                System.out.println("Ветка db");
                //Исключение для какого - то конкретного расширения
                File[] wList = searchDirectory.listFiles(new FileExcluder("-db"));
                //Положили в коллекцию
                for(int i=0; i<wList.length; i++){
                    res.add(wList[i]);
                }



            }
            else if (checkWithRegExp("^(-anyword)\\(.+",filesIgnored)){
                System.out.println("Ветка anyword");
                //Исключить из поиска файлы которые подходят под описание через пробел
                File[] wList = searchDirectory.listFiles();
                List <File> notIgnor = new ArrayList<>();
                //Положили в коллекцию
                for(int i=0; i<wList.length; i++){
                    notIgnor.add(wList[i]);
                }

                // Теперь исключение для большого количества слов через пробел
                String [] ignorList = filesIgnored.replace("-anyword(","").replace(")","").split(" ");
                setCurrentList(notIgnor);
                for (int k = 0; k<ignorList.length; k++){
                    String mask = ignorList[k];
                    currentList = currentList.stream().filter(
                            file -> !(file.getName().contains(mask)))
                            .collect(Collectors.toList());
                    setCurrentList(currentList);
                }

                res = currentList;

            }

            else if (checkWithRegExp("\\-.+",filesIgnored)){
                System.out.println("Ветка minus");
                //Ветка исключения для одного файла который подходит под описание

                File[] wList = searchDirectory.listFiles();
                List <File> notIgnor = new ArrayList<>();
                //Положили в коллекцию
                for(int i=0; i<wList.length; i++){
                    notIgnor.add(wList[i]);
                }
                // Пока исключения для одного
                res = notIgnor.stream().filter(
                        file -> !(file.getAbsolutePath().contains(filesIgnored.replace("-",""))))
                        .collect(Collectors.toList());


            }





        }catch (Exception e){

        }

        return res;

    }

    public List<File> doSort(List<File> nonSort, String sv){

        SortVars sortVariant;
        List<File> sortedResult;

        if (sv.equals("bydate") ){
            System.out.println("ветка bydate");
            sortVariant = SortVars.BYDATE;

            sortedResult = FileSorter.sortByDate(nonSort);


        }else        if (sv.equals("bysize")){
            System.out.println("ветка bysize");
            sortVariant = SortVars.BYSIZE;

            sortedResult = FileSorter.sortBySize(nonSort);
        }
        else {
            System.out.println("ветка byname");
            sortVariant = SortVars.BYNAME;

            sortedResult = FileSorter.sortByname(nonSort);
        }

        return sortedResult;
    }



    public static boolean checkWithRegExp(String regexp, String stringForMatch){
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(stringForMatch);
        return m.matches();
    }
}
