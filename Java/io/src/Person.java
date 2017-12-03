import java.io.*;

/**
 * Created by pc on 2017/12/3.
 */
public class Person implements Serializable {
    private Name userName;
    private String password;

    public Person(String userName, String password) {
        this.userName = new Name(userName);
        this.password = password;
    }

    public String toString() {
        return "userName:" + userName + "  password:" + password;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.out"));
        oos.writeObject("Save a object:\n");
        oos.writeObject(new Person("Bruce", "123456"));
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.out"));
        String s = (String)ois.readObject();
        Person p = (Person)ois.readObject();
        System.out.println(s + p);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(baos);
        oos2.writeObject("Save another object:\n");
        oos2.writeObject(new Person("Phil", "654321"));
        oos2.close();

        ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        s = (String)ois2.readObject();
        p = (Person)ois2.readObject();
        System.out.println(s + p);
    }
}