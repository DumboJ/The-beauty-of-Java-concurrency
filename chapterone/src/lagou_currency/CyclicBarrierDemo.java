package lagou_currency;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        List<String> s = new LinkedList<>();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                //模拟分组完成后异步调用接口
                System.out.println("凑齐十个客户号，开始调用");
                int size = s.size();
                System.out.println("list"+size);
                s.clear();
            }
        });

        List<String> customers = new LinkedList<>();
        //模拟开始接收到100个客户编号逻辑
        for (int i = 0; i < 100; i++) {
            customers.add(i + "");
        }
        //主程序，遍历集合，十个一组调用
        for (int i = 0; i < customers.size(); i++) {
            new Thread(new BarrierTask(s,customers.get(i),cyclicBarrier)).start();
        }
    }

    public static class BarrierTask implements Runnable {
        public List<String> customers;
        public String customId;
        private CyclicBarrier barrier;
        public BarrierTask(List<String> customers,String customId,CyclicBarrier barrier) {
            this.customId = customId;
            this.barrier = barrier;
            this.customers = customers;
        }
        @Override
        public void run() {
            System.out.println("准备接收到客户编号：" + customId);
            try {
                //模拟接口等待处理客户编号信息
                customers.add(customId);
                System.out.println("接收到客户编号：" + customId);

                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
