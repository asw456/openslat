package org.openslat.receiver;

import java.io.IOException;

import org.openslat.control.SlatMainController;
import org.openslat.jsonparser.SlatInputStore;
import org.openslat.jsonparser.SlatParser;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class TaskReceiver {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws java.io.IOException,
			java.lang.InterruptedException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		channel.basicQos(1);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			System.out.println(" [x] Received '" + message + "'");
			// TODO: this used to be a void method, is this OK
			String results = generateResults(message);
			System.out.println(" [x] Done");

			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	private static String generateResults(String inputString) {

		SlatInputStore slatInputStore;
		try {
			slatInputStore = SlatParser.parseInputJsonString(inputString);

			SlatMainController slatMainController = new SlatMainController();
			slatMainController.setCalculationOptions(slatInputStore
					.getCalculationOptions());
			slatMainController.setStructure(slatInputStore.getStructure());

			// and some magic happens
			String outputString = slatMainController.generateOutputString();

			return outputString;

		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
	}
}
