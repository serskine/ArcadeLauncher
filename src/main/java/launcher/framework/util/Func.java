package launcher.framework.util;

public class Func {
    public static void pause(final long msDelay) {
        if (msDelay>0) {
            try {
                Thread.sleep(msDelay);
            } catch (InterruptedException e) {
                // Do nothing.
            }
        }
    }
}
