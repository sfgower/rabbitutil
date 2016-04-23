package org.person.sfgower.rabbitutil.test;

/**
 * Created by TRINITY on 4/23/16.
 */

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import org.person.sfgower.rabbitutil.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by TRINITY on 4/17/16.
 *
 *
 */

public class BasicTest {

    /**
     * Create senders and receivers using a simple API.
     * A ReceiverFactory is used to generate receivers based on a consumer and
     * a configuration.
     *
     * While not supported presently, the intent is to add a QueueConfiguration parameter
     * to the ReceiverFactory.createReceiver() method, so that the actual queue itself
     * can be configured through the factory method.
     *
     * @throws Exception
     */


    @org.junit.Test
    public void basicTest()
            throws Exception {
        final String HOST = "localhost";
        final int PORT = 5672;
        final String TEST_QUEUE_NAME = "test-1";
        Sender sender = SenderFactory.createSender(HOST, PORT, TEST_QUEUE_NAME);
        assertNotNull(sender);
        ConsumerConfiguration consumerConfiguration = new ConsumerConfiguration();


        Receiver receiver = ReceiverFactory.createReceiver(
                "localhost",
                5672, TEST_QUEUE_NAME, EchoConsumer.class, consumerConfiguration);

        Receiver receiver2 = ReceiverFactory.createReceiver(
                "localhost",
                5672, TEST_QUEUE_NAME, EchoConsumer.class, consumerConfiguration);

        Thread t = new Thread(receiver);
        t.start();

        Thread t2 = new Thread(receiver2);
        t2.start();
        Thread.sleep(5000);

        String words[] = new String[]
                {
                        "hello","how","are","you?","I","am","fine","and","am","getting","better","all","the","time"
                };
        Set<String> sent = new HashSet<>();
        for (String word: words) {
            sender.send(word);
            sent.add(word);
        }
        Thread.sleep(5000);

        EchoConsumer consumer = (EchoConsumer)receiver.getConsumer();
        Set<String> result =  consumer.getResults();
        assertTrue(result.size() > 0);
        for (String word: result)
            sent.remove(word);

        EchoConsumer consumer2 = (EchoConsumer)receiver2.getConsumer();
        Set<String> result2 =  consumer2.getResults();
        assertTrue(result2.size() > 0);
        for (String word: result2)
            sent.remove(word);

        assertTrue(sent.isEmpty());

    }


    public static class EchoConsumer extends BaseConsumer implements Consumer {

        public static final String EMPTY_STRING ="";

        private Set<String> result = new HashSet<>();


        public EchoConsumer(Channel channel, ConsumerConfiguration consumerConfiguration)
        {
            super (channel,consumerConfiguration);
        }

        @Override
        public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {

            String word = new String(bytes).toString();
            result.add(word);


        }

        @Override
        public void init(ConsumerConfiguration consumerConfiguration)
        {

        }

        public Set<String> getResults()
        {
            return result;
        }
    }






}
