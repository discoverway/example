package rabbitmq.t3.broadcast;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer1 {

    private final static String EXCHANGE_NAME = "fanout";

    public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException,
	    ConsumerCancelledException, InterruptedException {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("54.215.185.67");
	factory.setUsername("discoverway");
	factory.setPassword("19830916");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	/** Declare Exchange */
	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	/** Get default queue name */
	String queueName = channel.queueDeclare().getQueue();
	channel.queueBind(queueName, EXCHANGE_NAME, "");
	QueueingConsumer consumer = new QueueingConsumer(channel);
	channel.basicConsume(queueName, true, consumer);
	System.out.println(" [*] Waiting for messages......");

	while (true) {
	    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	    String message = new String(delivery.getBody());

	    FileWriter fw = new FileWriter(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), true);
	    fw.write(message + "\r\n");
	    fw.flush();
	    fw.close();
	}

    }

}
