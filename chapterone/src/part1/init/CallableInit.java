package part1.init;

import java.util.concurrent.Callable;

/**
 * 实现Callable接口开启多线程
 * */
public class CallableInit {
    public static void main(String[] args) throws Exception {
        CallableTask task = new CallableTask();
        task.call();
    }
    private static class CallableTask implements Callable {
        @Override
        public String call() throws Exception {
            return "CallableTask start...";
        }
    }
}
