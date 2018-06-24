package exercise;

import pets.Cat;
import pets.Dog;
import pets.Individual;
import pets.Manx;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pc on 2018/6/24.
 */
public class RandomList<T> {
    private ArrayList<T> storage = new ArrayList<T>();
    private Random rand = new Random(47);
    public void add(T item) { storage.add(item); }
    public T select() {
        return storage.get(rand.nextInt(storage.size()));
    }
    public static void main(String[] args) {
        RandomList<String> rs = new RandomList<String>();
        for(String s: ("The quick brown fox jumped over " +
                "the lazy brown dog").split(" "))
            rs.add(s);
        for(int i = 0; i < 11; i++)
            System.out.print(rs.select() + " ");

        RandomList<Integer> rs1 = new RandomList<>();
        for(int i = 0; i < 5; i++)
            rs1.add(i);
        for(int i = 0; i < 11; i++)
            System.out.print(rs1.select() + " ");

        RandomList<Individual> rs2 = new RandomList<>();
        rs2.add(new Individual());
        rs2.add(new Cat());
        rs2.add(new Dog());
        rs2.add(new Manx());
        for(int i = 0; i < 11; i++)
            System.out.print(rs2.select() + " ");
    }
} /* Output:
brown over fox quick quick dog brown The brown lazy brown
*///:~

