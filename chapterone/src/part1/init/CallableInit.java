package part1.init;

import java.util.concurrent.Callable;

/**
 * 实现Callable接口开启多线程
 * */
public class CallableInit {
    public static void main(String[] args) {
        CallableTask task = new CallableTask();
    }
    private static class CallableTask implements Callable {
        @Override
        public Object call() throws Exception {
            return null;
        }
    }
}
