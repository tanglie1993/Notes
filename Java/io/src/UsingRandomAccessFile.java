import java.io.*;

public class UsingRandomAccessFile {
    static String file = "rtest.dat";

    public static void main(String[] args) throws IOException {
        check("Double");
        check("Int");
        check("Boolean");
        check("Byte");
        check("Char");
        check("Float");
        check("Long");
        check("Short");
    }

    private static void check(String type)  throws IOException {
        write(type);
        modify(type);
        display(type);
        new File(file).delete();
    }

    private static void display(String type) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        System.out.println("-------------");
        System.out.println("Type: " + type);
        for(int i = 0; i < 7; i++){
            System.out.print("Value " + i + ": ");
            switch(type){
                case "Double":
                    System.out.println(""+ rf.readDouble());
                    break;
                case "Int":
                    System.out.println(""+ rf.readInt());
                    break;
                case "Boolean":
                    System.out.println(""+ rf.readBoolean());
                    break;
                case "Byte":
                    System.out.println(""+ rf.readByte());
                    break;
                case "Char":
                    System.out.println(""+ rf.readChar());
                    break;
                case "Float":
                    System.out.println(""+ rf.readFloat());
                    break;
                case "Long":
                    System.out.println(""+ rf.readLong());
                    break;
                case "Short":
                    System.out.println(""+ rf.readShort());
                    break;
                case "Line":
                    System.out.println(""+ rf.readLine());
                    break;
            }
        }

        System.out.println(rf.readUTF());
        rf.close();
    }

    private static void modify(String type) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        switch(type){
            case "Double":
                rf.seek(5*8);
                rf.writeDouble(42.0001);
                break;
            case "Int":
                rf.seek(5*4);
                rf.writeInt(42);
                break;
            case "Boolean":
                rf.seek(5);
                rf.writeBoolean(true);
                break;
            case "Byte":
                rf.seek(5*1);
                rf.writeByte((byte) 0x42);
                break;
            case "Char":
                rf.seek(5*2);
                rf.writeChar('n');
                break;
            case "Float":
                rf.seek(5*4);
                rf.writeFloat(42.025f);
                break;
            case "Long":
                rf.seek(5*8);
                rf.writeLong(42L);
                break;
            case "Short":
                rf.seek(5*2);
                rf.writeShort(42);
                break;
        }
        rf.close();
    }

    private static void write(String type) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for(int i = 0; i < 7; i++){
            switch(type){
                case "Double":
                    rf.writeDouble(i);
                    break;
                case "Int":
                    rf.writeInt(i);
                    break;
                case "Boolean":
                    rf.writeBoolean(i % 2 == 0);
                    break;
                case "Byte":
                    rf.writeByte(i);
                    break;
                case "Char":
                    rf.writeChar('0' + i);
                    break;
                case "Float":
                    rf.writeFloat(i);
                    break;
                case "Long":
                    rf.writeLong(i);
                    break;
                case "Short":
                    rf.writeShort(i);
                    break;
            }
        }

        rf.writeUTF("The end of the file");
        rf.close();
    }
}