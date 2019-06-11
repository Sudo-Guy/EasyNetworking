package EasyNetworking.library;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * <h1>EasyServerSocket - EasyServerSocket class implements simplified methods for ServerSocket,</h1>
 *
 * <p>
 * EasyServerSocket class implements simplified methods for ServerSocket,
 * Simply made to make your life easier
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see ServerSocket
 * @see ServerHandler
 * @since 2019-05-20
 */

public class EasyServerSocket extends ServerSocket {

    /**
     * ServerHandler is used to hold the information of the input and output and the client Socket
     * @see ServerHandler
     * @see Handler
     */
    protected ServerHandler handler;

    protected String serverName;


    /**
     * Calls the Constructor in ServerSocket while also setting a default timeout of 10000 milliseconds
     *
     * @param port port number where EasyServerSocket will be created at
     * @throws IOException Throws IOException when port cannot be allocated
     * @see IOException
     * @see ServerSocket Constructor ServerSocket(in port)is called from this Constuctor
     */

    public EasyServerSocket(int port) throws IOException {
        super(port);
        try {
            this.setSoTimeout(10000);
            serverName = "Server";
        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * Calls the Constructor in ServerSocket while also setting a default timeout of 10000 milliseconds and naming error messages
     *
     * @param port       port number where EasyServerSocket will be created at
     * @param serverName This serverName will name error messages allowing debugging code to be easier
     * @throws IOException Throws IOException when port cannot be allocated
     * @see IOException
     * @see ServerSocket Constructor ServerSocket(in port)is called from this Constuctor
     */

    public EasyServerSocket(int port, String serverName) throws IOException {
        super(port);
        try {
            this.setSoTimeout(10000);
            this.serverName = serverName;
        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method will allow Clients connect to the Server
     * This method also creates a new server Handler
     * @param printOut Determines if program will print out Server and Connected Client details
     * @throws IOException This can be thrown at creation of new ServerHandler when super.accept() has failed
     * @see IOException
     * @see ServerHandler
     */

    public void accept(boolean printOut) {
        if (handler != null && handler.getClient() != null) {
            if (printOut)
                println("Server may already be connected to client");
        }
        try {
            if (printOut)
                println(this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());

            handler = new ServerHandler(this.accept());

            if (printOut)
                println("Just connected to " + handler.getClient().getRemoteSocketAddress() + "\nClient Name - " + handler.getClient().getInetAddress().getHostName() + "\nClient Address - " + handler.getClient().getInetAddress().getHostAddress());

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method will allow Clients connect to the Server
     * This method also creates a new ServerHandler
     *
     * @param timeout  Will determine how long the server will wait for a connection in milliseconds
     * @param printOut Determines if program will print out Server and Connected Client details
     * @throws IOException This can be thrown at creation of new ServerHandler when super.accept() has failed
     * @see IOException
     * @see ServerHandler
     */

    public void accept(int timeout, boolean printOut) {
        if (handler != null && handler.getClient() != null) {
            if (printOut)
                println("Server may already be connected to client");
        }
        try {
            this.setSoTimeout(timeout);
            if (printOut)
                println(this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());

            handler = new ServerHandler(this.accept());

            if (printOut)
                println("Just connected to " + handler.getClient().getRemoteSocketAddress() + "\nClient Name - " + handler.getClient().getInetAddress().getHostName() + "\nClient Address - " + handler.getClient().getInetAddress().getHostAddress());

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method allows you to send any objects to your connected Client
     *
     * @param object This is the object being sent to your Client
     */

    public void send(Object object) {
        if (handler == null)
            println("ServerHandler handler is null at " + this.getClass());

        this.handler.send(object);
    }

    /**
     * This method waits until a Object is sent by a Client
     *
     * @return This returns the Object sent by the Client
     */

    public Object receive() {
        if (handler == null)
            println("ServerHandler handler is null at " + this.getClass());

        return this.handler.receive();
    }


    /**
     * uses standard output and adds serverName - and creates new line
     *
     * @param print outputted text
     */

    public void println(String print) {
        System.out.println(serverName + " - " + print);
    }

    /**
     * uses standard output and adds serverName -
     *
     * @param print outputted text
     */

    public void print(String print) {
        System.out.print(serverName + " - " + print);
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
     * Used to return handler and allows access to internal output and input streams and client socket data
     *
     * @return ServerHandler
     * @see ServerHandler
     */

    public ServerHandler getHandler() {
        return handler;
    }

}
