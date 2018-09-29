package issac.study.tutorial.aop.proxy;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by issac.hu on 2018/9/27.
 */
public class ProceedImp implements Proceed {

    private MethodObj methodObj;

    private Proceed next;

    private Proceed callback;

    public ProceedImp(MethodObj methodObj, Proceed next) {
        this.methodObj = methodObj;
        this.next = next;
    }

    @Override
    public void execute() throws InvocationTargetException, IllegalAccessException {
        executeNext();
    }

    private void executeNext() throws InvocationTargetException, IllegalAccessException {
        if (next != null) {
            methodObj.getMethod().invoke(methodObj.getObj(), next);
        } else {
            if (this.callback == null) {
                this.callback = new Proceed() {
                    @Override
                    public void execute() throws InvocationTargetException, IllegalAccessException {
                        System.out.println("method in...");
                    }

                };
            }

            methodObj.getMethod().invoke(methodObj.getObj(), this.callback);
        }
    }


    public Proceed getCallback() {
        return callback;
    }

    public void setCallback(Proceed callback) {
        this.callback = callback;
    }


    public MethodObj getMethodObj() {
        return methodObj;
    }

    public void setMethodObj(MethodObj methodObj) {
        this.methodObj = methodObj;
    }

    public Proceed getNext() {
        return next;
    }

    public void setNext(Proceed next) {
        this.next = next;
    }
}
