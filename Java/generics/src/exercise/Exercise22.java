package exercise;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by pc on 2018/6/30.
 */
public class Exercise22 {

    public static Object createNew(String typename, Object arg) {
        try {
            return Class.forName(typename).getConstructor(arg.getClass()).newInstance(arg);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(createNew(String.class.getName(), "dddf").getClass().getName());
        System.out.println(createNew(String.class.getName(), "dddf"));

    }
}
