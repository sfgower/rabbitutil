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

    protected void init(ConsumerConfiguration consumerConfiguration)
    {

    }

}