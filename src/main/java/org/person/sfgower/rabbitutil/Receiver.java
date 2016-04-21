/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil;

import com.rabbitmq.client.Consumer;

/**
 * Created by TRINITY on 4/15/16.
 */

public interface Receiver extends Runnable,Endpoint {

    /**
     * Get the host for this receiver.
     * @return host
     */

    String getHost();

    /**
     * Get the port
     * @return port
     */

    int getPort();

    /**
     * Get the queue name
     * @return queue name
     */

    String getQueueName();

    /**
     * Close the connection
     */

    void releaseConnection();

    /**
     * Get the consumer.
     * @return
     */

    Consumer getConsumer();
}
