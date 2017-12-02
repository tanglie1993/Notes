import jdk.nashorn.internal.runtime.regexp.RegExp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by pc on 2017/12/2.
 */
public class SortedDirList {

    public static void main(String[] args) {
        String path = "D:/workspace/Notes/Java/io/files/";
        for(String string : new SortedDirList(path).list()){
            System.out.println(string);
        }
        System.out.println("----------------");
        for(String string : new SortedDirList(path).list("Getty.*")){
            System.out.println(string);
        }
        System.out.println("----------------");
        for(String string : new SortedDirList(path).list("G.+y.*")){
            System.out.println(string);
        }
        System.out.println("----------------");
        for(String string : new SortedDirList(path).list(".*")){
            System.out.println(string);
        }
        System.out.println("----------------");
        for(String string : new SortedDirList(path + "长干行二首/").list(1512222020000L)){
            System.out.println(string);
        }
    }

    private String directory;

    public SortedDirList(String directory) {
        this.directory = directory;
    }

    public String[] list(){
        return new File(directory).list();
    }

    public String[] list(String pattern){
        String[] list = new File(directory).list();
        if(list == null){
            return new String[0];
        }
        List<String> result = new ArrayList<>();
        int totalSize = 0;
        for(String fileName : list){
            if(Pattern.matches(pattern, fileName)){
                result.add(fileName);
                File file = new File(directory + "\\" + fileName);
                totalSize += file.length();
            }
        }
        String[] strings = new String[result.size()];
        System.out.println("totalSize: " + totalSize);
        return result.toArray(strings);
    }

    public String[] list(long modifyTimeStamp){
        String[] list = new File(directory).list();
        if(list == null){
            return new String[0];
        }
        List<String> result = new ArrayList<>();
        for(String fileName : list){
            File file = new File(directory + "\\" + fileName);
            if(file.exists() && file.lastModified() > modifyTimeStamp){
                result.add(fileName);
            }
        }
        String[] strings = new String[result.size()];
        return result.toArray(strings);
    }
}
