import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pc on 2017/12/2.
 */
public class LineReader {

    public static void main(String[] args) {
        read("D:/workspace/Notes/Java/io/files/Gettysburg Address.txt");
    }

    public static String read(String fileName) {
        StringBuilder sb = new StringBuilder();
        List<String> texts = new LinkedList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    new File(fileName).getAbsoluteFile()));
            try {
                String s;
                while((s = in.readLine()) != null) {
                    texts.add(s.toUpperCase());
                }
            } finally {
                in.close();
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
