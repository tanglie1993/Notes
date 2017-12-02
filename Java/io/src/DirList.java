import java.util.regex.*;
import java.io.*;
import java.util.*;

public class DirList {
    public static void main(String[] args) {
        File path = new File("D:/workspace/Notes/Java/io/files/");
        String pattern = "the people";
        String[] list;
        list = path.list();
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String dirItem : list){
            String content = TextUtil.read(path.getAbsolutePath() + "\\" + dirItem);
            if(content.contains(pattern)){
                System.out.println(dirItem);
            }
        }
    }
}

class DirFilter implements FilenameFilter {
    private Pattern pattern;
    public DirFilter(String regex) {
        pattern = Pattern.compile(regex);
    }
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
}