package issac.study.tutorial.thread.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncCglib implements MethodInterceptor {

    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void putPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    private Object target;//业务类对象，供代理方法中进行真正的业务方法调用

    //相当于JDK动态代理中的绑定
    public Object getInstance(Object target) {
        this.target = target;  //给业务对象赋值
        Enhancer enhancer = new Enhancer(); //创建加强器，用来创建动态代理类
        enhancer.setSuperclass(this.target.getClass());  //为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(this);
        // 创建动态代理类对象并返回
        return enhancer.create();
    }

    // 实现回调方法
    public Object intercept(final Object obj, Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        System.out.println("before...");

        Future<Object> submit = threadPool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    return proxy.invokeSuper(obj, args);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return null;

            }
        });

        System.out.println("after...");
        System.out.println(threadPool.toString());
        return submit;
    }
}