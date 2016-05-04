package rabbitmq.t1.simple;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws java.io.IOException, TimeoutException, ShutdownSignalException,
	    ConsumerCancelledException, InterruptedException {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("54.215.185.67");
	factory.setUsername("discoverway");
	factory.setPassword("19830916");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	channel.queueDeclare(QUEUE_NAME, false, false, false, null);

	QueueingConsumer consumer = new QueueingConsumer(channel);
	channel.basicConsume(QUEUE_NAME, true, consumer);
	System.out.println(" [*] Waiting for messages......");

	while (true) {
	    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	    String message = new String(delivery.getBody());
	    System.out.println(" [x] Received '" + message + "'");
	}
    }

}
