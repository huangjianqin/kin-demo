package org.kin.demo.java.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq client poll模式
 *
 * @author huangjianqin
 * @date 2021/1/21
 */
public class Poll {
    private static final String QUEUE_NAME = "poll";

    private static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost("localhost");
        factory.setUsername("rabbitmq");
        factory.setPassword("123456");
    }

    public static void main(String[] args) {
        final ExecutorService executors = Executors.newCachedThreadPool();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, true, null);

            executors.execute(Poll::multiConsumer);

            int count = 100;
            for (int i = 0; i < count; i++) {
                String message = "Hi, " + i + "!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            Thread.sleep(10 * 1000);
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        executors.shutdownNow();
    }

    private static void multiConsumer() {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            int sleepTime = 100;
            int count = 8 * 1000 / sleepTime;
            while (count > 0) {
                GetResponse response;
                while (Objects.nonNull(response = channel.basicGet(QUEUE_NAME, true))) {
                    System.out.println("Received: " + new String(response.getBody(), StandardCharsets.UTF_8));
                }
                count--;
                Thread.sleep(sleepTime);
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
