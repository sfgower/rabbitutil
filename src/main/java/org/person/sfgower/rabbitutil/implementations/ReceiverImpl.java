/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil.implementations;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.person.sfgower.rabbitutil.ConsumerConfiguration;
import org.person.sfgower.rabbitutil.PerProcessConnectionFactory;
import org.person.sfgower.rabbitutil.Receiver;

import java.lang.reflect.Constructor;

public class ReceiverImpl implements Receiver {



    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 5672;


    private String queueName;
    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private Class consumerClass = null;
    private Consumer consumer = null;
    private ConsumerConfiguration consumerConfiguration;

    private Connection connection;
    private Channel channel;

    public ReceiverImpl(String host,
                        int port,
                        String queueName,
                        Class consumerClass,
                        ConsumerConfiguration consumerConfiguration
    )
            throws Exception {
        this.host = host;
        this.port = port;
        this.queueName = queueName;
        this.consumerClass = consumerClass;
        this.consumerConfiguration = consumerConfiguration;

    }


    @Override
    public void run()
    {
     boolean done = false;
     System.out.println("Starting instantiation...");
     try {
         instantiateMe();
         done = true;
         System.out.println("Instantation done!");
     }
     catch (Exception any)
     {
      any.printStackTrace();
     }
     finally
     {
      System.out.println("done? ==? " + done);
     }



    }

    @Override
    public String getQueueName() {
        return queueName;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void releaseConnection()
    {
     if (connection != null)
         try
         {
          PerProcessConnectionFactory.releaseConnection(this,host,port);
          connection =null;
         }
         catch (Exception any)
         {

         }
    }

    public  void instantiateMe()
            throws Exception
    {

        connection = PerProcessConnectionFactory.getConnection(this,host,port);
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);


        consumer = instantiateConsumer(consumerClass,channel,consumerConfiguration);
        if (consumer != null) {
            channel.basicConsume(queueName, true, consumer);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        }



    }



    public static Consumer instantiateConsumer(Class consumerClass, Channel channel, ConsumerConfiguration consumerConfiguration)
    {
        try {
            Constructor c = consumerClass.getConstructor(new Class[]{Channel.class,ConsumerConfiguration.class});
            Object object = c.newInstance(new Object[]{channel,consumerConfiguration});
            return object instanceof Consumer ? (Consumer)object : null;
        }
        catch (Exception nsme)
        {
            nsme.printStackTrace();
            return null;
        }
    }

    public Consumer getConsumer()
    {
     return consumer;
    }
}
