package org.kin.demo.java.se;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author huangjianqin
 * @date 2022/1/25
 */
public class Lambda {
    private final int seed = ThreadLocalRandom.current().nextInt(10);

    @SuppressWarnings("unchecked")
    public void println(Random random, int x, int y) {
        //正常使用lambda
        consume(l -> System.out.println(random.nextInt() + x + y));

        //模仿jvm编译生成lambda过程
        try {
            Method method = Lambda.class.getDeclaredMethod("consume$$Lambda", Random.class, int.class, int.class, Lambda.class);
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle methodHandle = lookup.unreflect(method);
            Consumer<Lambda> consumer = (Consumer<Lambda>) LambdaMetafactory.metafactory(lookup,
                    "accept",
                    MethodType.methodType(Consumer.class, Random.class, int.class, int.class),
                    MethodType.methodType(void.class, Lambda.class).erase(),
                    methodHandle,
                    MethodType.methodType(void.class, Lambda.class)
            ).getTarget().invoke(random, x, y);
            consumer.accept(this);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void consume(Consumer<Lambda> consumer) {
        consumer.accept(this);
    }

    /**
     * 模仿jdk编译生成对应lambda方法体
     */
    private static void consume$$Lambda(Random random, int x, int y, Lambda lambda) {
        System.out.println(random.nextInt(100000) + x + y);
    }

    //------------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public void println2(Double d1, Double d2) {
        //正常使用lambda
        BiFunction<Double, Double, Double> func1 = this::doubleAdd;
        System.out.println(func1.apply(d1, d2));


        //模仿jvm编译生成lambda过程
        try {
            Method method = Lambda.class.getDeclaredMethod("doubleAdd", Double.class, Double.class);
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle methodHandle = lookup.unreflect(method);
            BiFunction<Double, Double, Double> func2 = (BiFunction<Double, Double, Double>) LambdaMetafactory.metafactory(lookup,
                    "apply",
                    MethodType.methodType(BiFunction.class, Lambda.class),
                    MethodType.methodType(Double.class, Double.class, Double.class).erase(),
                    methodHandle,
                    MethodType.methodType(Double.class, Double.class, Double.class)
            ).getTarget().invoke(this);
            func2.apply(d1, d2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private Double doubleAdd(Double d1, Double d2) {
        return d1 + d2 * seed;
    }

    //------------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        Lambda lambda = new Lambda();
        lambda.println(new Random(), 100, 200);
        lambda.println2(1.1, 2.2);
    }
}
