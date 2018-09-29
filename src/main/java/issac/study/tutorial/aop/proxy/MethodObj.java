package issac.study.tutorial.aop.proxy;

import java.lang.reflect.Method;

/**
 * Created by issac.hu on 2018/9/27.
 */
public class MethodObj {

    private Method method;
    private Object obj;


    public MethodObj(Method method, Object obj) {
        this.method = method;
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public MethodObj setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public MethodObj setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
