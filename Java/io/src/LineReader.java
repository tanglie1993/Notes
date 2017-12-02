import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pc on 2017/12/2.
 */
public class LineReader {

    public static void main(String[] args) {
        read("D:/workspace/Notes/Java/io/files/Gettysburg Address.txt", "equal");
    }

    public static String read(String fileName, String searchContent) {
        StringBuilder sb = new StringBuilder();
        List<String> texts = new LinkedList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    new File(fileName).getAbsoluteFile()));
            new File(fileName + " out").createNewFile();
            PrintWriter writer = new PrintWriter(fileName + " out");
            try {
                String s;
                int lineNumber = 0;
                while((s = in.readLine()) != null) {
                    if(s.contains(searchContent)){
                        texts.add(s.toUpperCase());
                    }
                    lineNumber++;
                    writer.print("" + lineNumber + " ");
                    writer.println(s);
                }
            } finally {
                in.close();
                writer.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        for(String string : texts){
            System.out.println(string);
        }
        return sb.toString();
    }
}
