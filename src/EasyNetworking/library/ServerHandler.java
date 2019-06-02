package EasyNetworking.library;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler extends Handler {

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
