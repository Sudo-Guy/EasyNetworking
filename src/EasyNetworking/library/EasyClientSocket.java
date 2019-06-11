package EasyNetworking.library;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * <h1>EasyClientSocket - EasyClientSocket simplifies the creation of client sockets allowing you to easily send data back and forth between a server without the extra hassle</h1>
 *
 * <p>
 * EasyClientSocket simplifies the creation of client sockets allowing you to easily send data back and forth between a server without the extra hassle
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see Socket
 * @see ClientHandler
 * @since 2019-05-20
 */

public class EasyClientSocket extends Socket {

    /**
     * This ClientHandler creates and manages the ObjectInputStream and ObjectOutputStream connected to the server
     */

    protected ClientHandler handler;

    /**
     * clientName is the String that will be printed before all class error messages and exceptions
     */

    private String clientName;


    /**
     * This constructor calls the super(); constructor of Socket
     * @see Socket
     */

    public EasyClientSocket() {
        super();
        clientName = "Client";
    }

    /**
     * This constructor calls the super(); constructor of Socket and clientName is instantiated (look at clientName documentation)
     *
     * @param clientName This is the String that will be printed
     * @see Socket
     */

    public EasyClientSocket(String clientName) {
        super();
        this.clientName = clientName;
    }

    /**
     * This method connect EasyClientSocket to a Server
     *
     * @param hostname This is either the name or ip of the server
     * @param port     This is where the server is located on the local network
     */

    public void connect(String hostname, int port) {
        try {
            println("Connecting...");
            super.connect(new InetSocketAddress(hostname, port));
            println("Connected to " + this.getInetAddress().getHostName());

            handler = new ClientHandler(this.getOutputStream(), this.getInputStream());

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method allows you to send any objects to your connected Server
     *
     * @param object This is the object being sent to your Server
     * @throws IOException Thrown when Output Stream is unable to write the object to itself
     * @see ClientHandler
     */

    public void send(Object object) {
        if (handler == null)
            println("ClientHandler handler is null at " + this.getClass());

        this.handler.send(object);
    }

    /**
     * This method waits until a Object is sent by a Server
     *
     * @return This returns the Object sent by the Server
     * @see ClientHandler
     */

    public Object receive() {
        if (handler == null)
            println("ClientHandler handler is null at " + this.getClass());

        return handler.receive();
    }

    /**
     * uses standard output and adds serverName - and creates new line
     *
     * @param print outputted text
     */

    public void println(String print) {
        System.out.println(clientName + " - " + print);
    }

    /**
     * uses standard output and adds serverName -
     *
     * @param print outputted text
     */

    public void print(String print) {
        System.out.print(clientName + " - " + print);
    }

    /**
     * Waits for inputted time
     *
     * @param time time to wait in milliseconds
     * @throws InterruptedException At Thread.sleep()
     * @see InterruptedException
     */

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException E) {
            println(E.toString());
        }
    }

    /**
     * Used to return handler and allows access to internal output and input streams connect to server
     *
     * @return Current ClientHandler
     * @see ClientHandler
     */

    public ClientHandler getHandler() {
        return handler;
    }

    public String getClientName() {
        return clientName;
    }
}
