package EasyNetworking.library;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * <h1>ServerHandler</h1>
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
     *
     */

    protected Socket client;

    public ServerHandler(ObjectOutputStream out, ObjectInputStream in, Socket client) {
        this.in = in;
        this.out = out;
        this.client = client;
    }

    public ServerHandler() {
        super();
    }

    public Socket getClient() {
        return client;
    }

    protected void setClient(Socket client) {
        this.client = client;
    }
}
