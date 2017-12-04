
public class Main {

    static class YieldThread extends Thread {

        final int name;

        public YieldThread(int name){
            this.name = name;
        }

        @Override
        public void run() {
            for(int i = 0; i < 3; i++){
                System.out.println(""+ name + ": " + i);
                Thread.yield();
            }
        }
    }
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            new YieldThread(i).start();
        }
    }
}
