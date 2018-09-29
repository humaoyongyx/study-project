package issac.study.tutorial.aop.aspect;

import issac.study.tutorial.aop.annotation.MyAround;
import issac.study.tutorial.aop.annotation.MyAspect;
import issac.study.tutorial.aop.proxy.Proceed;

/**
 * Created by issac.hu on 2018/9/27.
 */
@MyAspect
public class TestAspect2 {

    @MyAround("test")
    public void around1(Proceed proceed) throws Throwable {
        System.out.println("around2-1 before...");
        proceed.execute();
        System.out.println("around2-1 after...");
    }

    @MyAround("test")
    public void around2(Proceed proceed) throws Throwable {
        System.out.println("around2-2 before...");
        proceed.execute();
        System.out.println("around2-2 after...");
    }

}
