package exercise;

import util.Holder;

import java.util.*;

/**
 * Created by pc on 2018/6/30.
 */
public class Exercise29 {

    static void f1(Holder<List<?>> holder) {
        System.out.println("Calling methods for the Holder: ");
        System.out.println("holder: " + holder);
        System.out.println("holder.get(): " + holder.get());
        System.out.println("Calling holder.set(Arrays.asList(1,2,3)");
        holder.set(Arrays.asList(1,2,3));
        System.out.println("holder.get(): " + holder.get());
        int[] ia = {1,2,3};
        System.out.println("int[] ia = {1,2,3}");
        System.out.println("holder.equals(ia): " + holder.equals(ia));
        List iaList = Arrays.asList(ia);
        System.out.println("List iaList = Arrays.asList(ia)");
        System.out.println("holder.equals(iaList): " + holder.equals(iaList));
        List<Integer> l = new ArrayList<Integer>();
        for(int i = 1; i < 4; i++) l.add(i);
        System.out.println("l = List<Integer>() and add 1,2,3");
        System.out.println("holder.equals(l): " + holder.equals(l));
        System.out.println();

        System.out.println("Calling methods for the List: ");
        System.out.println("holder.get(): " + holder.get());
        System.out.println("holder.get().getClass(): " + holder.get().getClass());
        // Incompatible types:
        // List<? extends Integer> list = holder.get();
        // OK, but can't add anything:
        List<?> list = holder.get();
        System.out.println("list: " + list);
        // list.add(new Object());
        List list1 = holder.get(); // OK, but:
        // list1.add(new Object()); // Warning: unchecked call to add(E) and
        // UnsupportedOperationException at runtime
        System.out.println("list1.getClass(): " + list1.getClass());
        System.out.println("list.equals(list1): " + list.equals(list1));
        // list.clear(); // runtime UnsupportedOperationException
        System.out.println("list.contains(1): " + list.contains(1));
        Collection<Integer> c = new HashSet<Integer>();
        for(int i = 1; i < 4; i++) c.add(i);
        System.out.println("list.containsAll(c): " + list.containsAll(c));
        System.out.println("list.equals(c): " + list.equals(c));
        System.out.println("list = " + list);
        System.out.println("c = " + c);
        System.out.println("c.getClass(): " + c.getClass()); // c is not a List
        System.out.println("list.get(0) = " + list.get(0));
        System.out.println("list.hashCode() = " + list.hashCode());
        System.out.println("list.indexOf(2) = " + list.indexOf(2));
        ListIterator li = list.listIterator();
        System.out.println("After ListIterator li = list.listIterator():");
        System.out.println("li.next() = " + li.next());
        // runtime errors: UnsupportedOperationException:
        // list.remove(0);
        // list.removeAll(c);
        // list.retainAll(c1);
        // list.set(1,4); // compile error: set(int, ?) in list<?> cannot
        // be applied to (int,int)
        System.out.println("list.size() = " + list.size());
        System.out.println("list.subList(1,2) = " + list.subList(1,2));
        Object[] oa = list.toArray();
        System.out.println("After Object[] oa = list.toArray():");
        System.out.print("oa = ");
        for(Object o : oa) System.out.print(o + " ");
        System.out.println();
        // Error: runtime error ArrayStoreException:
        // Double[] da = list.toArray(new Double[3]);
        Number[] na = list.toArray(new Number[3]);
        System.out.println("After Number[] na = list.toArray(new Number[3]):");
        System.out.print("na = ");
        for(Number n : na) System.out.print(n + " ");
        System.out.println();
    }
    static void f2(List<Holder<?>> list) {
        System.out.println("Calling methods for the List: ");
        System.out.println("list = " + list);
        System.out.println("Adding Integer, String, Double");
        list.add(new Holder<Integer>(1));
        list.add(new Holder<String>("two"));
        list.add(new Holder<Double>(3.0));
        System.out.println("list = " + list);
        System.out.println("Elements of list: ");
        for(Holder h : list)
            System.out.println("h.get() = " + h.get());
        System.out.println("Adding Float");
        list.add(3, new Holder<Float>(4.0f));
        System.out.println("list = " + list);
        System.out.println("Elements of list: ");
        for(Holder h : list)
            System.out.println("h.get() = " + h.get());
        System.out.println("list.clear()");
        list.clear();
        System.out.println("list = " + list);
        Collection<Holder<?>> c =
                new ArrayList<Holder<?>>();
        c.add(new Holder<String>("one"));
        c.add(new Holder<Float>(2.0f));
        c.add(new Holder<Double>(3.0));
        list.addAll(c);
        System.out.println("Adding String, Float, Double");
        System.out.println("list = " + list);
        System.out.println("Elements of list: ");
        for(Holder h : list)
            System.out.println("h.get() = " + h.get().getClass().getSimpleName()
                    + " " + h.get());
        list.add(3, new Holder<String>("four"));
        System.out.println("Elements of list: ");
        for(Holder h : list)
            System.out.println("h.get() = " + h.get().getClass().getSimpleName()
                    + " " + h.get());
        Object listClone = ((ArrayList)list).clone();
        System.out.println("((ArrayList)list).clone() = " +  listClone);
        System.out.println("((ArrayList)list).clone().getClass() = " +
                ((ArrayList)list).clone().getClass());
        // Appears to be an ArrayList, but cannot assign:
        // ArrayList alistClone = ((ArrayList)list).clone(); // found Object
        System.out.println("Replacing element with h4, new Holder<Integer>(4)");
        Holder<Integer> h4 = new Holder<Integer>(4);
        list.set(3, h4);
        System.out.println("Elements of list Holders: ");
        for(Holder h : list)
            System.out.println(h.get().getClass().getSimpleName()
                    + " " + h.get());
        System.out.println("list.contains(list.set(3, h4): " +
                list.contains(list.set(3, h4)));
        System.out.println("list.contains(4): " + list.contains(4));
        System.out.println("list.contains(h4): " + list.contains(h4));
        System.out.println("Adding null member to list");
        list.add(null);
        System.out.println("list.contains(null): " + list.contains(null));
        System.out.println("list.get(0).get(): " + list.get(0).get());
        System.out.println("list.indexOf(h4) = " + list.indexOf(h4));
        System.out.println("list.indexOf(null) = " + list.indexOf(null));
        System.out.println("list.isEmpty(): " + list.isEmpty());
        System.out.println("list.lastIndexOf(null) = " + list.lastIndexOf(null));
        System.out.println("Removing index 0");
        list.remove(0);
        System.out.println("Elements of list Holders: ");
        for(Holder h : list) {
            if(h == null) System.out.println("null");
            else
                System.out.println(h.get().getClass().getSimpleName()
                        + " " + h.get());
        }
        System.out.println("Removing null");
        System.out.println("Elements of list Holders: ");
        for(Holder h : list) {
            if(h == null) System.out.println("null");
            else
                System.out.println(h.get().getClass().getSimpleName()
                        + " " + h.get());
        }
        // Error: removeRange has protected access:
        // ((ArrayList)list).removeRange(0,2);
        System.out.println("list.size() = " + list.size());
        Object[] oa = list.toArray();
        System.out.print("list.toArray() = ");
        for(int i = 0; i < oa.length; i++)
            print(oa[i] + " ");
        System.out.println();
        Holder[] ha = ((ArrayList<Holder<?>>)list).toArray(new Holder[4]);
        System.out.println("(ArrayList<Holder<?>>list).toArray(new Holder[4]) = ");
        for(int i = 0; i < ha.length; i++)
            print(ha[i] + " ");
        System.out.println();
        System.out.println("Holder[4] Holders are holding: ");
        for(Holder h : ha) {
            if(h == null) System.out.println("null");
            else System.out.println(h.get());
        }
        System.out.println();

        System.out.println("Calling methods for the Holder: ");
        System.out.println("list = " + list);
        print("Three Holders (one null) in list:");
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) == null) System.out.println("null");
            else
                System.out.println(list.get(i).getClass().getSimpleName());
        }
        list.remove(3);
        System.out.println("Holders are holding:");
        for(Holder h : list) System.out.println(h.get());
        Holder<?> h1 = list.get(0);
        Holder<?> h2 = list.get(1);
        Holder<?> h3 = list.get(2);
        System.out.println(list.get(0).getClass() + ": "
                + list.get(0).get().getClass().getSimpleName() + ", "
                + list.get(0).get());
        System.out.println(list.get(1).getClass() + ": "
                + list.get(1).get().getClass().getSimpleName() + ", "
                + list.get(1).get());
        System.out.println(list.get(2).getClass() + ": "
                + list.get(2).get().getClass().getSimpleName() + ", "
                + list.get(2).get());
        // Error: cannot set <?> to int or Object:
        // h3.set(5);
        // h2.set(new Object());

    }

    private static void print(String s) {
        System.out.print(s);
    }

    public static void main(String[] args) {
        System.out.println("new Holder<List<?>> holder");
        f1(new Holder<List<?>>());
        System.out.println();
        System.out.println("List<Holder<?>> list = new ArrayList<Holder<?>>();");
        List<Holder<?>> list = new ArrayList<Holder<?>>();
        f2(list);
    }
}
