package exercise;

/**
 * Created by pc on 2018/6/30.
 */
public class Exercise34 {

    static abstract class A <T extends A<T>> {
        abstract T method();
    };

    static class B extends A<B> {
        @Override
        B method() {
            try {
                return B.class.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    };



    public static void main(String[] args) {
        System.out.println(new B().method());
    }
}
