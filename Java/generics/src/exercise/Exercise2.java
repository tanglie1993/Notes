package exercise;

import pets.*;
import util.FiveTuple;
import util.FourTuple;

/**
 * Created by pc on 2018/6/24.
 */
public class Exercise2 {

    static class SixTuple<A,B,C,D,E,F> extends FiveTuple<A,B,C,D,E> {
        final F sixth;
        public SixTuple(A a, B b, C c, D d, E e,F f) {
            super(a, b, c, d, e);
            sixth = f;
        }
        public String toString() {
            return "(" + first + ", " + second + ", " +
                    third + ", " + fourth + ", " + fifth + ", " + sixth + ")";
        }
    }

    static class SixTupleNotGeneric {
        final Object first;
        final Object second;
        final Object third;
        final Object fourth;
        final Object fifth;
        final Object sixth;
        public SixTupleNotGeneric(Object a, Object b, Object c, Object d, Object e,Object f) {
            first = a;
            second = b;
            third = c;
            fourth = d;
            fifth = e;
            sixth = f;
        }
        public String toString() {
            return "(" + first + ", " + second + ", " +
                    third + ", " + fourth + ", " + fifth + ", " + sixth + ")";
        }
    }

    public static void main(String[] args) {
        SixTuple sixTuple = new SixTuple<Cat, Manx, Individual, String, Character, Integer>(new Cat(),new Manx(), new Individual(), "a", 'a', 1);
        System.out.println(sixTuple.toString());
        SixTupleNotGeneric sixTupleNotGeneric = new SixTupleNotGeneric(new Cat(),new Manx(), new Individual(), "a", 'a', 1);
        System.out.println(sixTupleNotGeneric.toString());
    }
}
