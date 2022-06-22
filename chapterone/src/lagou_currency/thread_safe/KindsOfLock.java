package lagou_currency.thread_safe;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 各种类型的锁相关问题
 * */
public class KindsOfLock {
    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
    private static final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    public static void main(String[] args) {
        /**
         * print:
         * Thread-0获取读锁，正在读取
         * Thread-1获取读锁，正在读取
         * Thread-1读锁释放
         * Thread-0读锁释放
         * Thread-2获取写锁，正在写入
         * Thread-2写锁释放
         * Thread-3获取写锁，正在写入
         * Thread-3写锁释放
         * @Result 读锁允许多个线程同时获取
         *         写锁获取后只有在释放后其它的写入才能获取锁
         *
         *         相比于 ReentrantLock 适用于一般场合，ReadWriteLock 适用于读多写少的情况
         * */
        new Thread(()->read()).start();
        new Thread(()->read()).start();
        new Thread(()->write()).start();
        new Thread(()->write()).start();
    }
    public static void read(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取读锁，正在读取");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println(Thread.currentThread().getName() + "读锁释放");
            readLock.unlock();
        }
    }
    public static void write(){
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取写锁，正在写入");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println(Thread.currentThread().getName() + "写锁释放");
            writeLock.unlock();
        }
    }
}
