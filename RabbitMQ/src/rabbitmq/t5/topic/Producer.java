package rabbitmq.t5.topic;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    private final static String EXCHANGE_NAME = "topic";

    public static void main(String[] args) throws IOException, TimeoutException {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("54.215.185.67");
	factory.setUsername("discoverway");
	factory.setPassword("19830916");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	/** Declare Exchange */
	channel.exchangeDeclare(EXCHANGE_NAME, "topic");

	String[] routingKeys = new String[] { "kernel.info", "cron.warning", "auth.info", "kernel.critical" };
	for (String routingKey : routingKeys) {
	    String message = UUID.randomUUID().toString().toUpperCase();
	    channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
	    System.out.println("[x] Sent routingKey = " + routingKey + " , message = " + message + ".");
	}

	channel.close();
	connection.close();
    }

}
