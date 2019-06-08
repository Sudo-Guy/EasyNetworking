package EasyNetworking.library;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * <h1>ServerHandler - ServerHandler is a class that extends Handler and adds a Client Socket</h1>
 *
 * <p>
 * ServerHandler is a class that extends Handler and adds a Client Socket
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see Handler
 * @see Socket
 * @since 2019-05-20
 */

public class ServerHandler extends Handler {

    /**
     * This variable hold the Client socket that a server connects too
     */

    protected Socket client;

    /**
     * This constructor stores a Client socket,creates and stores the means of sending and receiving data by ObjectStreams
     *
     * @param client This is the Client that you would like to store
     * @see ObjectInputStream
     * @see ObjectOutputStream
     */
    public ServerHandler(Socket client) {
        this.client = client;
        try {
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException E) {
            System.out.println(E);
        }
    }

    /**
     * This constructor requires no parameters but assumes that when you later use it you set the variables
     */
    public ServerHandler() {
        super();
    }

    /**
     * This method gets the Client socket
     * @return This method returns the Client Socket
     */

    public Socket getClient() {
        return client;
    }


    /**
     *  This method allows you to set the Client's socket
     * @param client This is the Client Socket that you would like to use
     */
    protected void setClient(Socket client) {
        this.client = client;
    }
}
