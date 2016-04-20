package t2.demo.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {

	private final static String QUEUE_NAME = "workqueue";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		int hashCode = Consumer.class.hashCode();
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("54.215.185.67");
	    factory.setUsername("discoverway");
	    factory.setPassword("19830916");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    /** Declare queue persistent */
	    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	    /** Take 1 message each time */
	    channel.basicQos(1);
	    
	    System.out.println(hashCode + " [#] Waiting for messages.");
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    /** Turn off auto acknowledge */
	    channel.basicConsume(QUEUE_NAME, false, consumer);
	    
	    while (true) {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            System.out.println(hashCode + " [x] Received '" + message + "'");  
            doWork(message);
            /** Send acknowledgment message */
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            System.out.println(hashCode + " [x] Done");  
        }
	}
	
	private static void doWork(String task) throws InterruptedException {
		if(task.endsWith(".1")) {
			Thread.sleep(10000);
		} else {
			Thread.sleep(1500);
		}
    }

}
