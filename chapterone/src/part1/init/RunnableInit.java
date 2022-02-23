package part1.init;
/**
 * 实现Runnable接口开启多线程
 * 执行同样代码需初始化不同对象start()
 * */
public class RunnableInit {
    public static void main(String[] args) {
        RunnableTask task = new RunnableTask();
        new Thread(task).start();
        new Thread(task).start();
    }

    private static class RunnableTask implements Runnable{
        /**
         * 重写run()
         * @Return  void
         * */
        @Override
        public void run() {
            System.out.println("Runnable Thread start...");
            System.out.println("process");
            System.out.println("婊子爱财妞爱俏");
        }
    }
}
