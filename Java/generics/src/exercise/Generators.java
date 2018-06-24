package exercise;

import exercise.coffee.Coffee;
import exercise.coffee.CoffeeGenerator;
import util.Generator;

import java.util.*;

public class Generators {
    public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {
        for(int i = 0; i < n; i++)
            coll.add(gen.next());
        return coll;
    }

    public static <T> Collection<T> fill(List<T> coll, Generator<T> gen, int n) {
        System.out.println("fill1 called!");
        for(int i = 0; i < n; i++)
            coll.add(gen.next());
        return coll;
    }

    public static <T> Collection<T> fill(LinkedList<T> coll, Generator<T> gen, int n) {
        System.out.println("fill2 called!");
        for(int i = 0; i < n; i++)
            coll.add(gen.next());
        return coll;
    }

    public static void main(String[] args) {
        Collection<Coffee> coffee = fill(new ArrayList<>(), new CoffeeGenerator(), 4);
        for(Coffee c : coffee)
            System.out.println(c);

        coffee = fill(new LinkedList<>(), new CoffeeGenerator(), 4);
        for(Coffee c : coffee)
            System.out.println(c);
    }
}