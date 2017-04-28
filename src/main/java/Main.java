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

        if(args.length > 0)  //если через консоль были введены аргументы
            System.out.println("Parameter was Catched" + args[0]);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            FileWriter writer = new FileWriter("result.txt", false);

            System.out.println("What directory will be scanning?");
            String directoryName = br.readLine();
            if (directoryName == null){
                throw new Exception("Error: directory is not specified");
            }
            System.out.println("What is the key to ignore?");
            String filesIgnored = br.readLine();
            if (filesIgnored.isEmpty()){
                filesIgnored = "";
            }
            System.out.println("What type of sorting?");
            String sortParam = br.readLine();
            if (sortParam.isEmpty() ){
                sortParam = "byname";
            }
            TextHandler th = new TextHandler(directoryName,filesIgnored.toLowerCase(),sortParam);
          
            th.run();

            List<File> listFiles =  th.getCurrentList();


            for(int i = 0; i < listFiles.size(); i++) {
                File curObject = listFiles.get(i);

                if(curObject.isDirectory()) {
                    String text = curObject.getName() + " (directory)" + "Was created " + new Date(curObject.lastModified());
                    writer.write(text);
                    writer.write("\n");
                }
                else {
                    String text = curObject.getName()
                            + " (" + curObject.length() + " byte)" + "Was created" + new Date(curObject.lastModified());
                    writer.write(text);
                    writer.write("\n");
                }
            }
            System.out.println("was found " + th.getFilesNumber() +
                    " files and " + th.getDirectoriesNumber() +
                    " directories." + "full size:" + th.getTotalLength());
            writer.close();
        }

        catch(Exception ex){

            System.out.println(ex.getMessage());
        }




    }

}
