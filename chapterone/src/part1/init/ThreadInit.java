package part1.init;
/**
 * 继承 Thread接口开启线程
 * run()中获取当前线程不需要使用Thread.currentThread()，this可获取
 * 执行同样代码需初始化不同继承实例对象start()
 * */
public class ThreadInit {
    public static void main(String[] args) {
        ThreadTask task = new ThreadTask();
        task.start();
        task.start();// Exception: java.lang.IllegalThreadStateException
    }

    private static class ThreadTask extends Thread {
        /**
         * @return void
         * */
        @Override
        public void run() {
            System.out.println("ThreadTask start...");
            boolean isSameThread = this == Thread.currentThread();
            System.out.println("this与Thread.currentThread()获取相同线程：" + isSameThread);
        }
    }
}
