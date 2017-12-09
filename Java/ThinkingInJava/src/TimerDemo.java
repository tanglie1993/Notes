import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by pc on 2017/12/6.
 */
public class TimerDemo {

    public static void main(String[] args) throws Exception {
        for(int i = 0; i < 10000; i++)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Hello world!");
                }
            }, 1000);
    }
}
