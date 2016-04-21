/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil;

import org.person.sfgower.rabbitutil.implementations.SenderImpl;

/**
 * Created by TRINITY on 4/15/16.
 */
public class SenderFactory {

    /**
     * Create a queue endpoint, so that
     * messages can be sent to that endpoint.
     * @param host
     * @param port
     * @param queueName
     * @return queue producer
     * @throws Exception
     */

    public static Sender createQueueProducer(String host, int port, String queueName)
            throws Exception
    {
        SenderImpl queueProducer = new SenderImpl(host,port,queueName);
        queueProducer.init();
        return queueProducer;
    }
}
