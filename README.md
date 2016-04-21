# rabbitutil

Some utility classes to make it easier to create sender and receiver endpoints using RabbitMQ.
==============================================================================================

Why I write this code?

When I went to create receivers in RabbitMQ, I couldn't do what I wanted to do: define a Consumer class,
and then quickly spin up one or more receivers. 

Now suppose one creates a very simple kind of Consumer - an EchoConsumer class that 
implements Consumer. This consumer does nothing more than print out the incoming
message to System.out.

Well, here is how one can spin up a Receiver endpoint using RabbitUtil.
You actually can do it all in a single line if you want, but the code below
is broken into several statements to make it more readable.

public class ReceiverTestDrive {

    public static void main(String args[])
            throws Exception
    {
        ConsumerConfiguration consumerConfiguration = new ConsumerConfiguration();

        Receiver receiver = ReceiverFactory.createQueueConsumer("localhost",
                5672,"my-queue",EchoConsumer.class,consumerConfiguration);

        Thread t = new Thread(receiver);
        t.start();
    }
}

That's it. 

This early version of RabbitUtil is limited in functionality. It is likely to change as my own experience 
with RabbitMQ deepens. Meanwhile I find it really simple to spin up endpoints now, for both sender and receiver
endpoints.



