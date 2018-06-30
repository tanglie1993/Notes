package exercise;

import java.util.*;

public class Exercise17 {

    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<T>(a);
        result.addAll(b);
        return result;
    }
    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<T>(a);
        result.retainAll(b);
        return result;
    }
    // Subtract subset from superset:
    public static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
        Set<T> result = new HashSet<T>(superset);
        result.removeAll(subset);
        return result;
    }

    // Reflexive--everything not in the intersection:
    public static <T> Set<T> complement(Set<T> a, Set<T> b) {
        return difference(union(a, b), intersection(a, b));
    }

    public static <T extends Enum<T>> EnumSet<T> union(EnumSet<T> a, EnumSet<T> b) {
        EnumSet<T> result = a.clone();
        result.addAll(b);
        return result;
    }
    public static <T extends Enum<T>> EnumSet<T> intersection(EnumSet<T> a, EnumSet<T> b) {
        EnumSet<T> result = a.clone();
        result.retainAll(b);
        return result;
    }
    // Subtract subset from superset:
    public static <T extends Enum<T>> EnumSet<T> difference(EnumSet<T> superset, EnumSet<T> subset) {
        EnumSet<T> result = superset.clone();
        result.removeAll(subset);
        return result;
    }

    // Reflexive--everything not in the intersection:
    public static <T extends Enum<T>> EnumSet<T> complement(EnumSet<T> a, EnumSet<T> b) {
        return difference(union(a, b), intersection(a, b));
    }

    public enum Numbers {
        ONE, TWO, THREE, FOUR, FIVE
    };

    public static void main(String[] args) {
        EnumSet<Numbers> set1 = EnumSet.range(Numbers.ONE, Numbers.THREE);
        EnumSet<Numbers> set2 = EnumSet.range(Numbers.THREE, Numbers.FIVE);
        System.out.println("set1: " + set1);
        System.out.println("set2: " + set2);
        System.out.println("union(set1, set2): " + union(set1, set2));
        Set<Numbers> subset = intersection(set1, set2);
        System.out.println("intersection(set1, set2): " + subset);
        System.out.println("difference(set1, set2): " +
                difference(set1, set2));
        System.out.println("difference(set2, subset): " +
                difference(set2, subset));
        System.out.println("complement(set1, set2): " +
                complement(set1, set2));
    }
} 
