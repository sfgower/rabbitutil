# rabbitutil

Some utility classes to make it easier to create sender and receiver endpoints using RabbitMQ.
==============================================================================================

Why did I write this code?

When I started using RabbitMQ, something seemed to be missing.

When I went to create receivers in RabbitMQ, I couldn't do what I wanted to do: define a Consumer class,
and then quickly spin up one or more receivers and senders. The abstraction just seemed kind
wrong to me.

Instead, I just wanted to have a receiver factory. I would hand the receiver factory a Consumer class
and a configuraiton, and it would return a Receiver, all ready to be spun up in a thread.

The trivial challenge here is that the RabbitMQ code requires that a Channel object be used to instantiate a consumer.
So to get pass this, a tiny bit of Java reflection is used to construct the Consumer
with both this Channel object and the ConsumerConfiguration object be passed as inputs
into the constructor. The init(..) method of the consumer class - extending from BaseConsumer - then initializes the consumer using information
from the consumer configuration object. The programmer that defines a consumer class is responsible
for defining the init(ConsumerConfiguration) method; this method will then initialize the consumer object.

While the code is pretty trivial, it is still quite useful. Now receivers, with their worker bees (consumers)
can now be spun up with trivial effort, and the code size is very small.

Here is a snippet of code to spin up a receiver based on an EchoConsumer.



        Receiver receiver = ReceiverFactory.createReceiver(
                "localhost",
                5672, "echo-queue", EchoConsumer.class, consumerConfiguration);

        Thread t = new Thread(receiver);
        t.start();

Here is the code for the EchoConsumer. Note that the EchoConsumer extends from BaseConsumer.


    public  class EchoConsumer extends BaseConsumer implements Consumer {


        public EchoConsumer(Channel channel, ConsumerConfiguration consumerConfiguration)
        {
            super (channel,consumerConfiguration);
        }

        @Override
        public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {


            String word = new String(bytes).toString();
            System.outprintln("Echo message:" + word);
        }

        @Override
        public void init(ConsumerConfiguration consumerConfiguration)
        {
         // No configuration of the consumer needed in this case.
         // But the init(ConsumerConfiguration) method must be defined.
        }


    }

A sender can then be created...

     Sender sender = SenderFactory.createSender("localhost", 5672, "echo-queue");
     sender.send("Please echo this message");

Of course, receivers can instantiate their own senders, and so send messages
to and from other queues as well.

This code was developed on Mac OSX and compiled and run under Java 1.8.
I would expect, however, that it would run on under operating systems and under Java 1.7 as well.

Note that a rabbitutil.jar file is available here at under the /target directory.
So if you don't want to deal with source, just use the jar.

Alternatively, if you are using maven, you can use the rabbitutil.jar as follows.
Create a directory from your base directory, such as /libs.

Then use wget to download the rabbitutil.jar.

wget https://raw.github.com/sfgower/rabbitutil/master/target/rabbitutil.jar

Download this is the appropriate ../libs directory.

     <dependency>
            <groupId>rabbitutil</groupId>
            <artifactId>rabbituil.0.1</artifactId>
            <scope>system</scope>
            <version>0.1</version>
            <systemPath>${basedir}/libs/rabbitutil.jar</systemPath>
        </dependency>

Now Maven will user this system path to acquire the binaries of the rabbitutil.jar.

I will be adding more unit tests!

Bugs:-

  -- calling getConsumer() on a Receiver is not retrieving a consumer object.
     This is a bad bug because in unit testing it is very convenitent
     to able to access a consumer object directly.
 

 
Here are a few notes and caveats:

   This early version of RabbitUtil is limited in functionality. For example, it doesn't support
   custom configuration of a queue *yet* but that is planned. Instead, all queues created are
   created in the same manner at present. So in some future version, when a ReceiverFactory can
   receiver a QueueConfiguration argument, that queue configuration would determine how the
   queue is configured and, possibly, other behaviors of the receiver.

   This code assumes that all endpoints use the same RabbitMQ instance.
   This assumption could be lifted, but today it is a restriction.

   In an environment where the RabbitMQ instance is remote, it is possible
   that this code will have to slightly changed. I developed this using a local RAbbitMQ
   server, and have not yet tested it with a remote RabbitMQ sever or cluster.

   The need to extend consumer subclasses from BaseConsumer could be easily lifted,
   but right now it seems ok as is. Your opinion may vary.

   At present, the init(..) method on BaseConsumer doesn't include the Channel object.
   Perhaps it should, but I don't have a use case for this.


 Any comments, especially for any problems in the code, or improvements, would be appreciated.






