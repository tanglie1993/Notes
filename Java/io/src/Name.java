import java.io.Serializable;

/**
 * Created by pc on 2017/12/3.
 */
public class Name implements Serializable {

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
