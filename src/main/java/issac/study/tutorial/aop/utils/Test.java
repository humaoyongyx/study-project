package issac.study.tutorial.aop.utils;

import issac.study.tutorial.aop.service.TestService;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * Created by issac.hu on 2018/9/27.
 */
public class Test {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException {
        BeanFactory.scanComponents(BeanFactory.PATH_COMPONENTS);
        TestService test = (TestService) BeanFactory.getBean("test");

        test.test();
    }


}
