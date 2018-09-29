package issac.study.tutorial.aop.utils;

import issac.study.tutorial.aop.annotation.MyComponent;
import issac.study.tutorial.aop.proxy.AroundAopCglib;
import issac.study.tutorial.thread.annotation.Bean;
import issac.study.tutorial.thread.annotation.Config;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


public class BeanFactory {

    public static Map<String, Object> beanMap = new ConcurrentHashMap<>();

    public static Object getBean(String name) {
        return beanMap.get(name);
    }

    public static void put(String name, Object obj) {
        beanMap.put(name, obj);
    }

    public static void scanConfigAndBeans(String path) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Reflections reflections = new Reflections(path);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Config.class);
        for (Class clazz : typesAnnotatedWith) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Bean bean = method.getAnnotation(Bean.class);
                if (bean != null) {
                    Object invoke = method.invoke(clazz.newInstance());
                    put(bean.value(), invoke);
                }
            }
        }
    }


    public static void scanComponents(String path) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(path);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(MyComponent.class);
        for (Class clazz : typesAnnotatedWith) {
            MyComponent annotation = (MyComponent) clazz.getAnnotation(MyComponent.class);
            String simpleName = annotation.value();
            AroundAopCglib aroundAopCglib = new AroundAopCglib();
            Object instance = aroundAopCglib.getInstance(clazz.newInstance());
            put(simpleName, instance);
        }

    }


}
