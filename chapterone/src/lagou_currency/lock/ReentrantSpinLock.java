package lagou_currency.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 可重入自旋锁实现
 * */
public class ReentrantSpinLock {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    //重入次数
    private int count = 0;
    public void lock(){
        Thread thread = Thread.currentThread();
        if (thread == owner.get()) {
            count++;
            return;
        }
        while (!owner.compareAndSet(null, thread)) {
            System.out.println("自旋中等待获取锁...");
        }
    }
    public void unLock(){
        Thread thread = Thread.currentThread();
        //只有持有锁的线程才可以解锁
        if (thread == owner.get()) {
            if (count > 0) {
                count--;
            } else {
                owner.set(null);
            }
        }
    }

    public static void main(String[] args) {
        ReentrantSpinLock lock = new ReentrantSpinLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("开始尝试获取自旋锁");
                lock.lock();
                try {
                    System.out.println("获取到了自旋锁"+Thread.currentThread().getName());
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unLock();
                    System.out.println("释放自旋锁" + Thread.currentThread().getName());
                }
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}