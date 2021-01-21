package org.kin.demo.java.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * 两个队列, 一个消费奇数, 一个消费偶数, 通过exchange区分奇偶, 然后把消息路由到对应的queue中
 *
 * @author huangjianqin
 * @date 2021/1/21
 */
public class Routing {
    private static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost("localhost");
        factory.setUsername("rabbitmq");
        factory.setPassword("123456");
    }


    private static final String[] QUEUE_NAMES = {"odd", "even"};
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {
        ExecutorService executors = Executors.newCachedThreadPool();
        int executorNum = 2;
        for (int i = 0; i < executorNum; i++) {
            int finalI = i;
            executors.execute(() -> multiConsumer(finalI));
        }
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAMES[0], false, false, true, null);
            channel.queueDeclare(QUEUE_NAMES[1], false, false, true, null);
            //根据key直接route到某个queue
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueBind(QUEUE_NAMES[0], EXCHANGE_NAME, QUEUE_NAMES[0]);
            channel.queueBind(QUEUE_NAMES[1], EXCHANGE_NAME, QUEUE_NAMES[1]);

            int count = 100;
            for (int i = 0; i < count; i++) {
                String message = "log" + i;
                channel.basicPublish(EXCHANGE_NAME, QUEUE_NAMES[i % 2], null, message.getBytes());
            }

            Thread.sleep(15 * 1000);
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        executors.shutdownNow();
    }

    private static void multiConsumer(int index) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAMES[index % 2], false, false, true, null);
            channel.basicQos(2);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.printf("[%d]Received: %s%n", index, message);
            };
            channel.basicConsume(QUEUE_NAMES[index % 2], true, deliverCallback, consumerTag -> {
            });
            Thread.sleep(8 * 1000);
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
