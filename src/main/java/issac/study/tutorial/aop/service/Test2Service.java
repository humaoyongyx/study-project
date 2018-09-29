package issac.study.tutorial.aop.service;

import issac.study.tutorial.aop.annotation.MyComponent;

/**
 * Created by issac.hu on 2018/9/27.
 */
@MyComponent("test2")
public class Test2Service {

    public void test2() {
        System.out.println("Test2Service...");
    }
}
