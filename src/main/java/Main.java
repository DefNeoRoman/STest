import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * Created by Пользователь on 26.04.2017.
 */
public class Main {

    public static void main(String[] args) {


        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {

            FileWriter writer = new FileWriter("result.txt", false);

            System.out.println("Какую директорию сканируем?");

            String directoryName = br.readLine();
            if (directoryName == null){
                throw new Exception("ошибка: не задана папка");
            }

            System.out.println("Ключ для игнора");

            String filesIgnored = br.readLine();
            if (filesIgnored.isEmpty()){
                filesIgnored = "";
            }


            System.out.println("Какая сортировка?");

            String sortParam = br.readLine();
            if (sortParam.isEmpty() ){
                sortParam = "byname";
            }
            TextHandler th = new TextHandler(directoryName,filesIgnored.toLowerCase(),sortParam);
            System.out.println("принято" + directoryName + filesIgnored + sortParam );
            th.run();

            List<File> listFiles =  th.getCurrentList();


            for(int i = 0; i < listFiles.size(); i++) {
                File curObject = listFiles.get(i);

                if(curObject.isDirectory()) {
                    String text = curObject.getName() + " (папка)" + "Был создан " + new Date(curObject.lastModified());
                    writer.write(text);
                    writer.write("\n");
                }
                else {
                    String text = curObject.getName()
                            + " (" + curObject.length() + " байт)" + "Был создан " + new Date(curObject.lastModified());
                    writer.write(text);
                    writer.write("\n");
                }
            }
            System.out.println("Найдено " + th.getFilesNumber() +
                    " файлов и " + th.getDirectoriesNumber() +
                    " папок." + "общий размер:" + th.getTotalLength());
            writer.close();
        }

        catch(Exception ex){

            System.out.println(ex.getMessage());
        }




    }

}
