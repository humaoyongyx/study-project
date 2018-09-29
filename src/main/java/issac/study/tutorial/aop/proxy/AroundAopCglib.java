package issac.study.tutorial.aop.proxy;

import issac.study.tutorial.aop.annotation.MyAround;
import issac.study.tutorial.aop.annotation.MyAspect;
import net.sf.cglib.proxy.MethodProxy;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class AroundAopCglib extends AbstractAopCglib {


    @Override
    public Object abstractIntercept(final Object obj, Method method, final Object[] args, final MethodProxy proxy) throws Throwable {

        List<MethodObj> list = scanAround("issac.study.tutorial.aop");
        ProceedImp proceed = proceedList(list, new Proceed() {
            @Override
            public void execute() throws Throwable {
                proxy.invokeSuper(obj, args);
            }
        });
        proceed.execute();

        return null;
    }

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }


    private ProceedImp proceedList(List<MethodObj> list, Proceed callback) {
        ProceedImp now = null;
        int size = list.size() - 1;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (i == size) {
                ProceedImp init = new ProceedImp(list.get(i), null);
                init.setCallback(callback);
                now = new ProceedImp(list.get(i - 1), init);
                i--;
            } else {
                now = new ProceedImp(list.get(i), now);
            }
        }
        return now;
    }

    private List<MethodObj> scanAround(String path) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(path);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(MyAspect.class);
        List<MethodObj> list = new ArrayList<>();
        for (Class clazz : typesAnnotatedWith) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Object newInstance = clazz.newInstance();
            for (Method method : declaredMethods) {
                MyAround annotation = method.getAnnotation(MyAround.class);
                if (annotation != null) {
                    MethodObj methodObj = new MethodObj(method, newInstance);
                    list.add(methodObj);
                }
            }
        }
        return list;
    }
}