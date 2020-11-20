package org.kin.demo.java.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huangjianqin
 * @date 2020/11/20
 */
public class FluxDemo {
    public static void main(String[] args) {
        demo6();
    }

    public static void demo1() {
        Flux<Integer> source = Flux.range(1, 20);
        source.subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("done"),
                //只请求10个
                sub -> sub.request(10));
    }

    public static void demo2() {
        Flux<Integer> source = Flux.range(1, 20);

        source.buffer(10) //缓存10次request,并组成一个list, 然后再执行subscribe操作
                .subscribe(
                        System.out::println,
                        System.err::println,
                        () -> System.out.println("done"),
                        //只请求10个
                        sub -> sub.request(10));
    }

    public static void demo3() {
        Flux.range(1, 10)
                //拉取元素时触发
                .doOnRequest(r -> System.out.println("request of " + r))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    public void hookOnSubscribe(Subscription subscription) {
                        request(ThreadLocalRandom.current().nextInt(100));
                    }

                    @Override
                    public void hookOnNext(Integer integer) {
                        //打印接收到第一个item
                        System.out.println("Cancelling after having received " + integer);
                        cancel();
                    }
                });
    }

    public static void demo4() {
        Flux<String> flux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    String pro = "3 x " + state + " = " + 3 * state;
                    System.out.println("生产 " + pro);
                    sink.next(pro);
                    if (state == 20) {
                        sink.complete();
                    }
                    return state + 1;
                },
                (lastState) -> {
                    System.out.println("clean last state");
                });
        flux.limitRate(5)//控制provider生产速率
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }

                    @Override
                    public void hookOnNext(String str) {
                        System.out.println(str);
//                request(2);
                    }
                });
    }

    public static void demo5() {
        //create支持多线程触发next()
        Flux<Integer> flux = Flux.create(sink -> {
            int round = 3;
            CountDownLatch latch = new CountDownLatch(round);
            for (int i = 0; i < round; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 10; j++) {
                        sink.next(j);
                    }
                    latch.countDown();
                }).start();
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sink.complete();
        });
        flux.subscribe(System.out::println);
        flux.blockLast();
    }

    public static void demo6() {
        //push只能一条线程触发next(), 所以此处打印的结果都是按顺序来
        Flux<Integer> flux = Flux.create(sink -> {
            int round = 3;
            CountDownLatch latch = new CountDownLatch(round);
            for (int i = 0; i < round; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 10; j++) {
                        sink.next(j);
                    }
                    latch.countDown();
                }).start();
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sink.complete();
        });
        flux.subscribe(System.out::println);
        flux.blockLast();
    }
}
