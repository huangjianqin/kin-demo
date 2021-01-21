package org.kin.demo.java.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 发送消息到queue, 然后consume
 *
 * @author huangjianqin
 * @date 2021/1/21
 */
public class HelloWorld {
    private static final String QUEUE_NAME = "HELLO";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rabbitmq");
        factory.setPassword("123456");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("Received " + new String(delivery.getBody(), StandardCharsets.UTF_8));
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

            int count = 100;
            for (int i = 0; i < count; i++) {
                String message = "Hi, " + i + "!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            Thread.sleep(5 * 1000);
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
