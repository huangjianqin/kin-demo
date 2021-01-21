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
 * 随机创建n个queue(对应n个消费者)并绑定到一个exchange
 *
 * @author huangjianqin
 * @date 2021/1/21
 */
public class PublishSubscribe {
    private static final String EXCHANGE_NAME = "logs";


    private static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost("localhost");
        factory.setUsername("rabbitmq");
        factory.setPassword("123456");
    }

    public static void main(String[] args) {
        ExecutorService executors = Executors.newCachedThreadPool();
        int executorNum = 2;
        for (int i = 0; i < executorNum; i++) {
            int finalI = i;
            executors.execute(() -> multiConsumer(finalI));
        }
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //把所有消息推给binded queue, 每个queue拥有部分消息, 但不重复
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            int count = 100;
            for (int i = 0; i < count; i++) {
                String message = "log" + i;
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
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
            //让broker随机一个non-durable, exclusive, autodelete queue name,
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            channel.basicQos(2);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.printf("[%d]Received: %s%n", index, message);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
            Thread.sleep(8 * 1000);
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
