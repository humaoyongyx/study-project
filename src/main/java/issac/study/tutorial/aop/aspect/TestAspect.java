package issac.study.tutorial.aop.aspect;

import issac.study.tutorial.aop.annotation.MyAround;
import issac.study.tutorial.aop.annotation.MyAspect;
import issac.study.tutorial.aop.proxy.Proceed;

/**
 * Created by issac.hu on 2018/9/27.
 */
@MyAspect
public class TestAspect {

    @MyAround("")
    public void around1(Proceed proceed) throws Throwable {
        System.out.println("around1 before...");
        proceed.execute();
        System.out.println("around1 after...");
    }

    @MyAround("")
    public void around2(Proceed proceed) throws Throwable {
        System.out.println("around2 before...");
        proceed.execute();
        System.out.println("around2 after...");
    }

    @MyAround("")
    public void around3(Proceed proceed) throws Throwable {
        System.out.println("around3 before...");
        proceed.execute();
        System.out.println("around3 after...");
    }
}
