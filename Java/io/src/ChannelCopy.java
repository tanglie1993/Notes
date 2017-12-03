import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class ChannelCopy {
    private static final int BSIZE = 1024;
    public static void main(String[] args) throws Exception {
        try {
            Reader rd = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\workspace\\Notes\\Java\\io\\files\\Gettysburg Address.txt")));
            CharBuffer chbuff = CharBuffer.allocate(1024);
            while(rd.read(chbuff) > 0){
                chbuff.flip();
                while(chbuff.hasRemaining()){
                    char ch =  chbuff.get();
                    System.out.print(ch);
                }
                chbuff.clear();
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
