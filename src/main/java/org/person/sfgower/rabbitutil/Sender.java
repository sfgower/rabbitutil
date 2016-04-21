/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil;

/**
 * Created by TRINITY on 4/15/16.
 */
public interface Sender extends Endpoint {

    /**
     * Get the host of the queue.
     * @return host
     */

    String getHost();

    /**
     * Get the port.
     * @return port
     */

    int getPort();

    /**
     * Get the queuename
     * @return queuename
     */

    String getQueueName();

    /**
     * Send a message to the queue.
     * @param message
     * @throws Exception
     */

    void send(String message) throws Exception;

    /**
     * Close the queue endpoint.
     * @throws Exception
     */
    void close()
            throws Exception;
}
