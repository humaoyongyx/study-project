package issac.study.tutorial.aop.service;

import issac.study.tutorial.aop.annotation.MyComponent;

/**
 * Created by issac.hu on 2018/9/27.
 */
@MyComponent("test")
public class TestService {

    public void test() {
        System.out.println("TestService...");
    }
}
