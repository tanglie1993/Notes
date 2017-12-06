/**
 * Created by pc on 2017/12/6.
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    public static synchronized int nextSerialNumber() {
        return serialNumber++; // Not thread-safe
    }
}
