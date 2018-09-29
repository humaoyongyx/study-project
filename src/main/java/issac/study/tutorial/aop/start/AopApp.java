package issac.study.tutorial.aop.start;


import issac.study.tutorial.aop.service.TestService;
import issac.study.tutorial.aop.utils.BeanFactory;

import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 */
public class AopApp {

    public static void main(String[] args) throws Throwable {
        AopDemoStart();
    }

    public static void AopDemoStart() throws InterruptedException, ExecutionException, InstantiationException, IllegalAccessException {
        BeanFactory.scanComponents(issac.study.tutorial.aop.utils.BeanFactory.PATH_COMPONENTS);
        TestService test = (TestService) BeanFactory.getBean("test");
        test.test();
       /* Test2Service test2 = (Test2Service)BeanFactory.getBean("test2");
        test2.test2();*/
    }


}