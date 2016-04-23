/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil;

import org.person.sfgower.rabbitutil.implementations.ReceiverImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by TRINITY on 4/15/16.
 */
public class ReceiverFactory  {

    private static Logger logger = LoggerFactory.getLogger(ReceiverFactory.class);
    /**
     * Create a queue endpoint, so that
     * messages can be sent to that endpoint.
     * @param queueName
     * @return queue consumer
     * @throws Exception
     */

    public static Receiver createReceiver(String host,
                                          int port,
                                          String queueName,
                                          Class consumerClass,
                                          ConsumerConfiguration consumerConfiguration)
            throws Exception
    {

            logger.info("Creating receiver with consumer class: " + consumerClass.getName());
            return new ReceiverImpl(host,port,queueName,consumerClass,consumerConfiguration);

    }



}
