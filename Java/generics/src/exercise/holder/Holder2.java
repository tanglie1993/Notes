package exercise.holder;//: generics/exercise.holder.Holder2.java

public class Holder2 {
  private Object a;
  public Holder2(Object a) { this.a = a; }
  public void set(Object a) { this.a = a; }
  public Object get() { return a; }
  public static void main(String[] args) {
    Holder2 h2 = new Holder2(new Holder1.Automobile());
    Holder1.Automobile a = (Holder1.Automobile)h2.get();
    h2.set("Not an Automobile");
    String s = (String)h2.get();
    h2.set(1); // Autoboxes to Integer
    Integer x = (Integer)h2.get();
  }
} ///:~
