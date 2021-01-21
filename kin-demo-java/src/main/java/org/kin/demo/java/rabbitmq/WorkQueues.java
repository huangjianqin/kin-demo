package org.kin.demo.java.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author huangjianqin
 * @date 2021/1/21
 */
public class WorkQueues {
    private static final String QUEUE_NAME = "HELLO";
    private static ConnectionFactory factory = new ConnectionFactory();
    private static final String DURABLE_QUEUE_NAME = "duable_queue";

    static {
        factory.setHost("localhost");
        factory.setUsername("rabbitmq");
        factory.setPassword("123456");
    }

    public static void main(String[] args) {
        //1.
//        manualAck();

        //2.1 send
//        sendDurableMsg();
        //2.2 restart mq broker, then begin consume
//        consumeDurableMsg();


    }

    /**
     * 手动确认ack
     */
    private static void manualAck() {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("Received " + new String(delivery.getBody(), StandardCharsets.UTF_8));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Done! ");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
            });

            int count = 10;
            for (int i = 0; i < count; i++) {
                String message = "Hi, " + i + "!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            Thread.sleep(8 * 1000);
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * produce durable message
     */
    private static void sendDurableMsg() {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(DURABLE_QUEUE_NAME, true, false, false, null);

            int count = 100;
            for (int i = 0; i < count; i++) {
                String message = "durable message" + i + "!";
                channel.basicPublish("", DURABLE_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            }
            Thread.sleep(5 * 1000);
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * consume durable message
     */
    private static void consumeDurableMsg() {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.queueDeclare(DURABLE_QUEUE_NAME, true, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("Received " + new String(delivery.getBody(), StandardCharsets.UTF_8));
            };
            channel.basicConsume(DURABLE_QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
            Thread.sleep(5 * 1000);
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
