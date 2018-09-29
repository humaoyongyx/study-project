package issac.study.tutorial.thread.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 第四步：使用线程工厂创建线程池，并调用其execute方法
 */
public class ThreadExceptionUncaughtExceptionHandler {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HanlderThreadFactory());
        exec.execute(new ExceptionThread());
    }
}