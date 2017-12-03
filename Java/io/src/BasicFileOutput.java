import java.io.*;

public class BasicFileOutput {
    private static String file = "BasicFileOutput.out";
    public static void main(String[] args) throws IOException {
        BufferedWrite();
        write();
    }

    private static void write() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            doWrite(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void BufferedWrite() throws IOException {
        PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(file)));
        doWrite(out);
    }

    private static void doWrite(PrintWriter out) throws IOException {
        long startTimeStamp = System.currentTimeMillis();
        BufferedReader in = new BufferedReader(
                new StringReader(
                        BufferedInputFile.read("D:/workspace/Notes/Java/io/files/红楼梦.txt")));
        int lineCount = 1;
        String s;
        while((s = in.readLine()) != null )
            out.println(lineCount++ + ": " + s);
        out.close();
        // Show the stored file:
        System.out.println("用时： " + (System.currentTimeMillis() - startTimeStamp));
    }
} /* (Execute to see output) *///:~