package exercise;//: generics/GenericMethods.java

public class GenericMethods {
  public <T,U,V> void f(T x, U y, V z) {
    System.out.println(x.getClass().getName());
    System.out.println(y.getClass().getName());
    System.out.println(z.getClass().getName());
  }
  public static void main(String[] args) {
    GenericMethods gm = new GenericMethods();
    gm.f(gm,  1,  'c');
  }
} /* Output:
java.lang.String
java.lang.Integer
java.lang.Double
java.lang.Float
java.lang.Character
GenericMethods
*///:~
