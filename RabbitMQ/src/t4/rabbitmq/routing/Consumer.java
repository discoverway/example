package t4.rabbitmq.routing;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer {

	private final static String EXCHANGE_NAME = "direct";
	private static final String[] LEVELS = { "INFO", "WARNING", "ERROR" };
	
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("54.215.185.67");
	    factory.setUsername("discoverway");
	    factory.setPassword("19830916");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    /** Declare Exchange */
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct" );
	    /** Get default queue name */
	    String queueName = channel.queueDeclare().getQueue();
	    String level = getLevel();
	    channel.queueBind(queueName, EXCHANGE_NAME, level);
	    QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        System.out.println(" [*] Waiting for [" + level + "] logs......");
        
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }

	}
	
	private static String getLevel() {
        Random random = new Random();
        return LEVELS[random.nextInt(3)];
    }
}
