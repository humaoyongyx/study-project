package issac.study.tutorial.aop.proxy;

import issac.study.tutorial.aop.annotation.MyAfter;
import issac.study.tutorial.aop.annotation.MyAround;
import issac.study.tutorial.aop.annotation.MyAspect;
import issac.study.tutorial.aop.annotation.MyBefore;
import issac.study.tutorial.aop.utils.BeanFactory;
import issac.study.tutorial.aop.utils.RegUtils;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class AroundAopCglib extends AbstractAopCglib {


    @Override
    public Object abstractIntercept(final Object obj, Method method, final Object[] args, final MethodProxy proxy) throws Throwable {

        // System.out.println(method.toString());
        List<MethodObj> list = scanAspectSubType(BeanFactory.PATH_ASPECT, method.toString(), MyAround.class);
        if (list != null && list.size() > 0) {
            ProceedImp proceed = proceedList(list, new Proceed() {
                @Override
                public void execute() throws Throwable {
                    proxy.invokeSuper(obj, args);
                }
            });
            proceed.execute();
        }

        return null;
    }

    @Override
    public void before(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        List<MethodObj> list = scanAspectSubType(BeanFactory.PATH_ASPECT, method.toString(), MyBefore.class);
        if (list != null && list.size() > 0) {
            for (MethodObj methodObj : list) {
                methodObj.getMethod().invoke(methodObj.getObj());
            }
        }

    }

    @Override
    public void after(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        List<MethodObj> list = scanAspectSubType(BeanFactory.PATH_ASPECT, method.toString(), MyAfter.class);
        if (list != null && list.size() > 0) {
            for (MethodObj methodObj : list) {
                methodObj.getMethod().invoke(methodObj.getObj());
            }
        }
    }


    private ProceedImp proceedList(List<MethodObj> list, Proceed callback) {
        ProceedImp now = null;
        int size = list.size() - 1;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (size == 0) {
                now = new ProceedImp(list.get(0), null);
                now.setCallback(callback);
                break;
            }
            if (i > 0 && i == size) {
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


    private List<MethodObj> scanAspectSubType(String path, String methodName, Class sub) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(path);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(MyAspect.class);
        List<MethodObj> list = new ArrayList<>();
        for (Class clazz : typesAnnotatedWith) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Object newInstance = clazz.newInstance();
            for (Method method : declaredMethods) {
                Annotation mySub = method.getAnnotation(sub);
                String pattern = "";
                if (mySub != null) {
                    if (sub.isAssignableFrom(MyBefore.class)) {
                        pattern = ((MyBefore) mySub).value();
                    } else if (sub.isAssignableFrom(MyAround.class)) {
                        pattern = ((MyAround) mySub).value();
                    } else if (sub.isAssignableFrom(MyAfter.class)) {
                        pattern = ((MyAfter) mySub).value();
                    }
                }
                if (StringUtils.isNotBlank(pattern)) {
                    if (RegUtils.isMatch(pattern, methodName)) {
                        MethodObj methodObj = new MethodObj(method, newInstance);
                        list.add(methodObj);
                    }
                }
            }
        }
        return list;
    }
}