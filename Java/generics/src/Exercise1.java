import pets.Rat;
import pets.Rodent;

/**
 * Created by pc on 2018/6/24.
 */
public class Exercise1 {

    public static void main(String[] args) {
        Holder3<Rodent> h3 = new Holder3<>(new Rodent());
        Rodent a = h3.get();
        System.out.println(a.toString());
        h3.set(new Rat());
        a = h3.get();
        System.out.println(a.toString());
    }
}
