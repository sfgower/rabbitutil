/**

         Apache License
         Version 2.0, January 2004
         http://www.apache.org/licenses/
**/

package org.person.sfgower.rabbitutil;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by TRINITY on 4/17/16.
 *
 *
 */
public class PerProcessConnectionFactory {

    private static Connection connection = null;
    private static Set<Endpoint> endpoints = new HashSet<>();

    /**
     *
     * @param endpoint
     * @param host
     * @param port
     * @return
     * @throws Exception
     */

    public synchronized static Connection getConnection(Endpoint endpoint, String host, int port)
            throws Exception {
        if (connection != null) {
            endpoints.add(endpoint);
            return connection;
        }
        if ((connection != null) && (! (connection.getPort() != port)))
        {
         throw new Exception("Connection already bound but to different port: " + connection.getPort());
        }
        else {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            try {
                connection = factory.newConnection();
                endpoints.add(endpoint);
                return connection;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Exception e = new Exception("No connection");
                e.initCause(throwable);
                throw e;
            }
        }
    }

    public synchronized static int getNumberOfConnectedEndpoints()
    {
     return endpoints.size();
    }

    public synchronized boolean isConnected(Endpoint endpoint)
    {
     return endpoints.contains(endpoint);
    }

    public synchronized static boolean releaseConnection(Endpoint endpoint, String host, int port)
            throws Exception {
        if (connection == null)
            return false;
        if (endpoints.contains(endpoint))
        {
         endpoints.remove(endpoint);
         return true;
        }
        else
        {
         return false;
        }
    }


    public synchronized static boolean closeConnection(Endpoint endpoint, String host, int port)
            throws Exception {
        if (connection==null)
            return false;
        if (! endpoints.isEmpty())
            return false;
        try {
            connection.close();
            return true;
        }
        catch (Exception any)
        {
         return false;
        }

    }

}
