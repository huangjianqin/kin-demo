package org.kin.demo.java.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

/**
 * @author huangjianqin
 * @date 2021/1/21
 */
public class Topics {
    private static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost("localhost");
        factory.setUsername("rabbitmq");
        factory.setPassword("123456");
    }


    private static final String[] QUEUE_NAMES = {"q1", "q2", "q3"};
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            for (String queueName : QUEUE_NAMES) {
                channel.queueDeclare(queueName, false, false, true, null);
            }
            //* (star) can substitute for exactly one word.
            //# (hash) can substitute for zero or more words.
            //根据key与message内容的匹配进行route到某个queue
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            for (String queueName : QUEUE_NAMES) {
                channel.queueBind(queueName, EXCHANGE_NAME, queueName + ".*");
            }
            channel.queueBind(QUEUE_NAMES[2], EXCHANGE_NAME, "#");

            //配置client端消息发送确认
            channel.confirmSelect();
            channel.addConfirmListener((sequenceNumber, multiple) -> {
                // when message is confirmed
            }, (sequenceNumber, multiple) -> {
                //when message is nack-ed
            });

            ExecutorService executors = Executors.newCachedThreadPool();
            int executorNum = QUEUE_NAMES.length;
            for (int i = 0; i < executorNum; i++) {
                int finalI = i;
                executors.execute(() -> multiConsumer(finalI));
            }

            int count = 100;
            for (int i = 0; i < count; i++) {
                final ThreadLocalRandom random = ThreadLocalRandom.current();
                String message = QUEUE_NAMES[random.nextInt(QUEUE_NAMES.length)] + "." + random.nextInt(10000);
                channel.basicPublish(EXCHANGE_NAME, message, null, message.getBytes());
            }
            //等待broker确认消息发送成功
            //channel.waitForConfirmsOrDie(5_000);

            Thread.sleep(15 * 1000);
            executors.shutdownNow();
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void multiConsumer(int index) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.basicQos(2);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.printf("[%s]Received: %s%n", QUEUE_NAMES[index], message);
            };
            channel.basicConsume(QUEUE_NAMES[index], true, deliverCallback, consumerTag -> {
            });
            Thread.sleep(8 * 1000);
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
