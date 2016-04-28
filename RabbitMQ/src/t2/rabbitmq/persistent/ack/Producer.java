package t2.rabbitmq.persistent.ack;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

	private final static String QUEUE_NAME = "workqueue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("54.215.185.67");
	    factory.setUsername("discoverway");
	    factory.setPassword("19830916");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    /** Declare queue persistent */
	    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	    
	    for (int i = 0; i < 10; i++) {  
            String dots = "";  
            for (int j = 0; j <= i; j++) {  
                dots += ".";  
            }  
            String message = "helloworld" + dots+dots.length();
            /** Mark message persistent */
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());  
            System.out.println(" [x] Sent '" + message + "'");  
        }
	    
	    channel.close();
	    connection.close();
	}
}
