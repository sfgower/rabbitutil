/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

/**
 * Created by TRINITY on 4/17/16.
 */


public abstract class BaseConsumer extends DefaultConsumer implements Consumer {


    public BaseConsumer(Channel channel,ConsumerConfiguration consumerConfiguration)
    {
        super(channel);
        init(consumerConfiguration);

    }

    public abstract void init(ConsumerConfiguration consumerConfiguration);


}
