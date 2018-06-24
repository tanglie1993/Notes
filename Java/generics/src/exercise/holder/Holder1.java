package exercise.holder;//: generics/exercise.holder.Holder1.java

public class Holder1 {

  public static class Automobile {}

  private Automobile a;
  public Holder1(Automobile a) { this.a = a; }
  Automobile get() { return a; }
} ///:~
