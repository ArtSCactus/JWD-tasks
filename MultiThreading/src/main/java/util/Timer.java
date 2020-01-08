package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Timer implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Timer.class);
    private int value;
    private boolean isExpired = false;
    private boolean isKilled = false;
    private boolean isStopped = false;
    private int startValue;
    private ReentrantLock LOCKER = new ReentrantLock();

    public Timer(int startValue) {
        this.startValue = startValue;
    }

    @Override
    public void run() {
        LOGGER.debug("Timer successfully started.");
        value = startValue;
        while (!isKilled) {
            while (value > 0 & !isStopped) {
                isExpired = false;
                value--;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (value == 0) {
                    LOGGER.debug("Timer has expired.");
                    isExpired = true;
                }
            }

        }
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void stop() {
        isStopped = true;
    }

    public void resume() {
        isStopped = false;
    }

    public void kill() {
        isStopped = true;
        isKilled = true;
        isStopped = false;
    }

    public void revive() {
        isKilled = false;
    }

    public void reset() {
        LOCKER.lock();
        LOGGER.debug("Timer has been reset");
        value = startValue;
        isKilled = false;
        isStopped = false;
        isExpired = false;
        LOCKER.unlock();
    }
}
