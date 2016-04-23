/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil;

import org.person.sfgower.rabbitutil.implementations.SenderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by TRINITY on 4/15/16.
 */
public class SenderFactory {

    private static Logger logger = LoggerFactory.getLogger(SenderFactory.class);
    /**
     * Create a queue endpoint, so that
     * messages can be sent to that endpoint.
     * @param host
     * @param port
     * @param queueName
     * @return queue producer
     * @throws Exception
     */

    public static Sender createSender(String host, int port, String queueName)
            throws Exception
    {
        logger.info("Creating sender>> host: " + host +
        ", port: " + port + ", queueName: " + queueName);
        return new SenderImpl(host,port,queueName).init();

    }
}
