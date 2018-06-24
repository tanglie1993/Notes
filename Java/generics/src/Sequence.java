import java.lang.reflect.Array;

/**
 * Created by pc on 2018/6/24.
 */

interface Selector {
    boolean end();
    Object current();
    void next();
}

public class Sequence<T> {
    private T[] items;
    private int next = 0;
    public Sequence(Class<T> type, int size) { items = (T[]) Array.newInstance(type, size); }
    public void add(T x) {
        if(next < items.length)
            items[next++] = x;
    }
    private class SequenceSelector implements Selector {
        private int i = 0;
        public boolean end() { return i == items.length; }
        public T current() { return items[i]; }
        public void next() { if(i < items.length) i++; }
    }
    public Selector selector() {
        return new SequenceSelector();
    }
    public static void main(String[] args) {
        Sequence sequence = new Sequence(String.class,10);
        for(int i = 0; i < 10; i++)
            sequence.add(Integer.toString(i));
        Selector selector = sequence.selector();
        while(!selector.end()) {
            System.out.print(selector.current() + " ");
            selector.next();
        }
    }
}
