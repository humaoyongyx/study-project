package issac.study.tutorial.thread.service;

import issac.study.tutorial.thread.annotation.Async;
import issac.study.tutorial.thread.annotation.Component;


@Component("testService")
public class TestService {

    @Async("pool2")
    public Object test() {
        System.out.println(Thread.currentThread().toString());
        System.out.println("test");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "this is for test";
    }


    public void test2() {
        System.out.println("test");
    }

}
