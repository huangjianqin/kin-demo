package org.kin.demo.reactor.netty;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpClient;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author huangjianqin
 * @date 2020/11/24
 */
public class ReactorNettyDemo {
    private static Collection<Connection> connections = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        DisposableServer server =
                TcpServer.create()
                        .port(9001)
                        .wiretap(true)
                        .doOnBind(c -> {
                            System.out.println("binded" + c);
                        })
                        .doOnConnection(c -> {
                            System.out.println("connected" + c);
                            connections.add(c);
                        })
                        //直接send就好, 应该是框架帮我们自动调用了subscribe
                        .handle((in, out) -> out.sendString(in.receive()
                                .asString()
                                .map(String::toUpperCase)))
                        .bindNow();
        //定时心跳
        Flux<String> tick = Flux.generate((sink) -> sink.next("tick")).delayElements(Duration.ofSeconds(1)).map(Object::toString);
        Disposable tickSub = tick.subscribe(s -> connections.forEach(c -> c.outbound().sendString(Mono.just(s)).then().subscribe()));

        Connection connect = TcpClient.create()
                .doOnConnected(System.out::println)
                .port(9001)
                .wiretap(true)
                .doOnConnected(c -> {
                    c.inbound().receive().asString().subscribe(System.out::println);
                })
                .connectNow();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next.equals("end")) {
                break;
            }
            //掉subscribe才能真正发送
            connect.outbound().sendString(Mono.just(next)).then().subscribe();
        }

        tickSub.dispose();
        connect.dispose();
        server.dispose();
    }
}
