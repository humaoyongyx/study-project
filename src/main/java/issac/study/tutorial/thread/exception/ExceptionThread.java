package issac.study.tutorial.thread.exception;

/*
 * 第三步：我们的任务可能会抛出异常
 * 显示的抛出一个exception
 */

class ExceptionThread implements Runnable {
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}