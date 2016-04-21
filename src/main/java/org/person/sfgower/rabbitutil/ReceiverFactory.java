package org.person.sfgower.rabbitutil;

import org.person.sfgower.rabbitutil.implementations.ReceiverImpl;

/**
 * Created by TRINITY on 4/15/16.
 */
public class ReceiverFactory  {

    /**
     * Create a queue endpoint, so that
     * messages can be sent to that endpoint.
     * @param queueName
     * @return queue consumer
     * @throws Exception
     */

    public static Receiver createQueueConsumer(String host,
                                               int port,
                                               String queueName,
                                               Class consumerClass,
                                               ConsumerConfiguration consumerConfiguration)
            throws Exception
    {


            return new ReceiverImpl(host,port,queueName,consumerClass,consumerConfiguration);

    }



}
