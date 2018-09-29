package issac.study.tutorial.thread.utils;

import issac.study.tutorial.thread.annotation.Async;
import issac.study.tutorial.thread.annotation.Bean;
import issac.study.tutorial.thread.annotation.Component;
import issac.study.tutorial.thread.annotation.Config;
import issac.study.tutorial.thread.proxy.AsyncCglib;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;


public class BeanFactory {

    public static Map<String, Object> beanMap = new ConcurrentHashMap<>();

    public static Object getBean(String name) {
        return beanMap.get(name);
    }

    public static void put(String name, Object obj) {
        beanMap.put(name, obj);
    }

    public static void scanBeans(String path) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Reflections reflections = new Reflections(path);//"issac.study.tutorial.thread"
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


    public static void scanComponentsAndAsync(String path) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(path);//"issac.study.tutorial.thread"
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class);
        for (Class clazz : typesAnnotatedWith) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Async async = method.getAnnotation(Async.class);
                if (async != null) {
                    AsyncCglib facadeCglib = new AsyncCglib();
                    getBean(async.value());
                    Object bean = getBean(async.value());
                    if (bean != null) {
                        facadeCglib.putPool((ExecutorService) bean);
                    }
                    Object obj = facadeCglib.getInstance(clazz.newInstance());
                    Component annotation = (Component) clazz.getAnnotation(Component.class);
                    put(annotation.value(), obj);
                }

            }
        }

    }


}
