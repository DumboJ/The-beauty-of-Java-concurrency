package lagou_currency.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 谈论读写锁的降级问题：修改缓存数据
 *
 * */
public class LockReduceInRank {
    Object data;
    volatile boolean cacheValid;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private void processCache(){
        rwl.readLock().lock();
        if (!cacheValid) {
            //获取写锁前需要先释放读锁
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {
                //再次判断cache有效性，以防在读锁释放，写锁获取的过程中有其它线程对数据进行了更改
                if (!cacheValid) {
                    data = new String("change cache");
                    cacheValid = true;
                }
                /**
                 * 考虑一直使用写锁的话，只有等写锁释放后才可以获取读锁，如果后续有线程来读，那我一直持有写锁的话是不能读的，只有等写锁释放了，我其它线程才可以获取到读锁进行读，那我干脆在这个时候降级，读锁的话多个线程可共享
                 * 我们在第二次缓存可用性判断时候修改了数据，后面仅对数据读取，如果一直使用写锁，就不能让多个线程同时来读取了，持有写锁是浪费资源的，降低了整体的效率
                 *此时利用锁的降级是最好的办法，可以提高整体性能
                 *
                 * 后续未释放写锁的情况下获取读锁，读写锁的降级
                 * */
                rwl.readLock().lock();//读写锁降级后，这时候读锁是多个线程共享的，读操作不受影响
            }finally {
                //释放了写锁，但是依然持有读锁
                rwl.writeLock().unlock();
            }

            try {
                System.out.println(data);
            }finally {
                //释放读锁
                rwl.readLock().unlock();
            }
        }
    }

    /**
     * 测试读写锁升级
     * 现象：只会打印---获取到了数据
     * 结论：ReentrantReadWriteLock 不支持写锁到读锁的升级
     */
    public void upGrade() {
        rwl.readLock().lock();
        System.out.println("获取到了读锁");
        rwl.writeLock().lock();
        System.out.println("锁升级成功");
    }
    /**
     * 前提-如果线程都申请读锁，是可以多个线程同时持有的，可是如果是写锁，只能有一个线程持有，并且不可能存在读锁和写锁同时持有的情况。
     * 锁升级可能出现的问题：A B C 三个线程都持有读锁 此时A读锁要升级到写锁  需要等到B C都释放手中读锁，这时候确实可以升级成功
     *                  but. 如果A B都要升级成读锁，这时候他们就会相互等待对方释放读锁，就出现了死锁现象
     *
     *                 读写锁可以升级，前提是需要保证每次只有一个线程可以升级，ReentrantLock 不支持
     * */
}
