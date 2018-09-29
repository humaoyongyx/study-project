package issac.study.tutorial.aop.start;


import issac.study.tutorial.aop.service.TestService;
import issac.study.tutorial.aop.utils.BeanFactory;

import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Throwable {
        AopDemoStart();
    }

    public static void AopDemoStart() throws InterruptedException, ExecutionException, InstantiationException, IllegalAccessException {
        BeanFactory.scanComponents(issac.study.tutorial.aop.utils.BeanFactory.PATH_COMPONENTS);
        TestService test = (issac.study.tutorial.aop.service.TestService) issac.study.tutorial.aop.utils.BeanFactory.getBean("test");
        test.test();
    }


}