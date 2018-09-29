package issac.study.tutorial;

import issac.study.tutorial.thread.service.TestService;
import issac.study.tutorial.thread.utils.BeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws Throwable {

        AopDemoStart();

    }

    public static void ThreadDemoStart() throws IllegalAccessException, InvocationTargetException, InstantiationException, ExecutionException, InterruptedException {
        BeanFactory.scanBeans("issac.study.tutorial.thread");
        BeanFactory.scanComponentsAndAsync("issac.study.tutorial.thread");
        Object pool1 = BeanFactory.getBean("pool1");
        Object pool2 = BeanFactory.getBean("pool2");
        System.out.println("pool1->" + pool1);
        System.out.println("pool2->" + pool2);
        TestService testService = (TestService) BeanFactory.getBean("testService");
        for (int i = 0; i < 5; i++) {
            Future future = (Future) testService.test();
            //   System.out.println(future.get());
        }
    }

    public static void AopDemoStart() throws InterruptedException, ExecutionException, InstantiationException, IllegalAccessException {
        issac.study.tutorial.aop.utils.BeanFactory.scanComponents(issac.study.tutorial.aop.utils.BeanFactory.PATH_COMPONENTS);
        issac.study.tutorial.aop.service.TestService test = (issac.study.tutorial.aop.service.TestService) issac.study.tutorial.aop.utils.BeanFactory.getBean("test");
        test.test();
    }


}