package org.kin.demo.java.se;

import java.lang.invoke.*;

/**
 * @author huangjianqin
 * @date 2022/2/13
 */
public class LambdaReceiverBridge extends LambdaReceiver_A {
    interface IA {
        int m(LambdaReceiver_A x);
    }

    static MethodHandles.Lookup l;
    static MethodHandle h;

    private static MethodType mt(Class<?> k) {
        return MethodType.methodType(k);
    }

    private static MethodType mt(Class<?> k, Class<?> k2) {
        return MethodType.methodType(k, k2);
    }

    public static void main(String[] args) throws Throwable {
        l = MethodHandles.lookup();
        h = l.findVirtual(LambdaReceiver_A.class, "f", mt(int.class));
        MethodType X = mt(int.class, LambdaReceiverBridge.class);
        MethodType A = mt(int.class, LambdaReceiver_A.class);
        MethodType mti = mt(IA.class);
        CallSite cs = LambdaMetafactory.altMetafactory(l, "m", mti, X, h, X,
                LambdaMetafactory.FLAG_BRIDGES, 1, A);
        IA p = (IA) cs.dynamicInvoker().invoke();
        LambdaReceiver_A lra = new LambdaReceiver_A();
        try {
            p.m(lra);
        } catch (ClassCastException cce) {
            return;
        }
        throw new AssertionError("CCE expected");
    }
}
