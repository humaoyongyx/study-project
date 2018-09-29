package issac.study.tutorial.aop.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class AopCglib extends AbstractAopCglib {

    private ExecutorService threadPool = null;
    private boolean sync;

    public void putPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public Object getInstance(Object target, boolean sync) {
        this.sync = sync;
        if (!sync) {
            threadPool = Executors.newFixedThreadPool(10);
        }
        return super.getInstance(target);
    }


    @Override
    public Object abstractIntercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        return execute(obj, method, args, proxy);
    }


    private Object execute(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        if (!this.sync) {
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
            return submit;
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }

}