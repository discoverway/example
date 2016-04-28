package t4.rabbitmq.routing;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	private final static String EXCHANGE_NAME = "direct";
	private static final String[] LEVELS = { "INFO", "WARNING", "ERROR" };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("54.215.185.67");
	    factory.setUsername("discoverway");
	    factory.setPassword("19830916");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    /** Declare Exchange */
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
	    
	    for (int i = 0; i < 6; i++) {  
            String level = getLevel();  
            String message = level + "_log :" + UUID.randomUUID().toString().toUpperCase();
            /** Set routing key */
            channel.basicPublish(EXCHANGE_NAME, level, null, message.getBytes());  
            System.out.println(" [x] Sent '" + message + "'");  
        }  
  
        channel.close();  
        connection.close();
	}
	
	private static String getLevel() {  
        Random random = new Random();  
        return LEVELS[random.nextInt(3)];  
    }

}
