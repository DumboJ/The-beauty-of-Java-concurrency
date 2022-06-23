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
        /**
         * 读写锁在针对不公平锁时候有两类策略：
         *          1、允许插队          有可能出现读锁在前，中间有写锁时进去队列等待，后来读锁可以插队，导致队列里的写错长时间得不到执行而出现饥饿现象
         *          2、不允许插队         RetrantLock 使用该策略
         * ReentrantLock 的读写锁实现策略是：
         *          写锁在队列前则不允许插队，可以防止读锁插队太多，写锁获取出现“饥饿现象”
         * */
        new Thread(()->read(),"read-0").start();
        new Thread(()->read(),"read-1").start();
        new Thread(()->write(),"write-0").start();
        new Thread(()->read(),"read-2").start();
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println(Thread.currentThread().getName() + "写锁释放");
            writeLock.unlock();
        }
    }
}
