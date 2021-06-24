package part1.sleep;

import java.util.concurrent.locks.ReentrantLock;

public class SleepDemo {
    public final static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        new Thread(()->{
            lock.lock();
            try {
                System.out.println("ThreadA get is in sleep");
                Thread.sleep(1000);
                System.out.println("ThreadA get is in awaked");
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();
        new Thread(()->{
            lock.lock();
            try {
                System.out.println("ThreadB get is in sleep");
                Thread.sleep(1000);
                System.out.println("ThreadB get is in awaked");
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();
    }
}
