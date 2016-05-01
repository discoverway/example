package t3.rabbitmq.broadcast;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    private final static String EXCHANGE_NAME = "fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("54.215.185.67");
	factory.setUsername("discoverway");
	factory.setPassword("19830916");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	/** Declare Exchange */
	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	String message = "Broadcast content....";
	channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
	System.out.println(" [x] Sent '" + message + "'");

	channel.close();
	connection.close();
    }

}
