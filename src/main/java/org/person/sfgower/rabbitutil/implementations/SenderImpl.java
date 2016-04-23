/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil.implementations;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.person.sfgower.rabbitutil.PerProcessConnectionFactory;
import org.person.sfgower.rabbitutil.Sender;

public class SenderImpl implements Sender {


    //public static final int DEFAULT_PORT = 5672;


    private String queueName ;
    private String host;
    private int port;
    Connection connection = null;
    Channel channel = null;


    public SenderImpl(String host, int port, String queueName)
    {
     this.host = host;
     this.port = port;
     this.queueName = queueName;

    }

    public SenderImpl init()
            throws Exception
    {

        connection = PerProcessConnectionFactory.getConnection(this,host,port);
        channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);
        return this;


    }

    @Override
    public void send(String message) throws Exception {

        channel.basicPublish("", queueName,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("UTF-8"));
    }

    @Override
    public void close()
            throws Exception
    {
        channel.close();
        releaseConnection();
    }


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

    @Override
    public String getHost()
    {
     return host;
    }

    @Override
    public int getPort()
    {
     return port;
    }

    public String getQueueName()
    {
     return queueName;
    }


}
