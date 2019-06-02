package EasyNetworking.library;

import java.io.*;
import java.net.ServerSocket;

/**
 * <h1>EasyServerSocket</h1>
 *
 * <p>
 * EasyServerSocket class implements simplified methods for ServerSocket,
 * Simply as made to make your life easier
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see ServerSocket
 * @since 2019-05-20
 */

public class EasyServerSocket extends ServerSocket {

    /**
     * ServerHandler is used to hold the information of the input and output and the client Socket
     * @see ServerHandler
     * @see Handler
     */
    protected ServerHandler handler;


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
        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method will allow Clients connect to the Server
     * This method also creates a new server Handler
     * @param printOut Determines if program will print out Server and Connected Client details
     */

    public void accept(boolean printOut) {
        if (handler == null)
            handler = new ServerHandler();
        if (handler.getClient() != null) {
            if (printOut)
                System.out.println("Server may already be connected to client");
        }
        try {
            if (printOut)
                System.out.println("Server - " + this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());
            handler.setClient(this.accept());
            if (printOut)
                System.out.println("Server - Just connected to " + handler.getClient().getRemoteSocketAddress() + "\nClient Name - " + handler.getClient().getInetAddress().getHostName() + "\nClient Address - " + handler.getClient().getInetAddress().getHostAddress());

            handler.setInputStream(new ObjectInputStream(handler.getClient().getInputStream()));
            handler.setOutputStream(new ObjectOutputStream(handler.getClient().getOutputStream()));

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method will allow Clients connect to the Server
     * This method also creates a new server Handler
     *
     * @param timeout  Will determine how long the server will wait for a connection in milliseconds
     * @param printOut Determines if program will print out Server and Connected Client details
     * @throws IOException Thrown when Input or Output Stream are not able to be instantiated
     * @see IOException
     */

    public void accept(int timeout, boolean printOut) {
        if (handler == null)
            handler = new ServerHandler();
        if (handler.getClient() != null) {
            if (printOut)
                System.out.println("Server may already be connected to client");
        }
        try {
            this.setSoTimeout(timeout);
            if (printOut)
                System.out.println("Server - " + this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());
            handler.setClient(this.accept());
            if (printOut)
                System.out.println("Server - Just connected to " + handler.getClient().getRemoteSocketAddress() + "\nClient Name - " + handler.getClient().getInetAddress().getHostName() + "\nClient Address - " + handler.getClient().getInetAddress().getHostAddress());

            handler.setInputStream(new ObjectInputStream(handler.getClient().getInputStream()));
            handler.setOutputStream(new ObjectOutputStream(handler.getClient().getOutputStream()));

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method allows you to send any objects to your connected Client
     *
     * @param object This is the object being sent to your Client
     * @throws IOException Thrown when Output Stream is unable to write the object to itself
     */

    public void send(Object object) {
        if (handler == null) {
            println("ServerHandler handler is null at " + this.getClass());
        }
        try {
            this.handler.getOutputStream().writeObject(serialize(object));
        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * Helper method for the send(); function, turns Objects into byte[]
     *
     * @param obj This is the object being serialized
     * @return serialized Object as a byte[]
     * @throws IOException Thrown when Output Streams are unable to write the object to itself
     */

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /**
     * This method waits until a Object is sent by a Client
     *
     * @return This returns the Object sent by the Client
     * @throws IOException Thrown when Input Streams are unable to write the object to itself
     */

    public Object receive() {
        if (handler == null) {
            println("ServerHandler handler is null at " + this.getClass());
        }
        try {
            return deserialize((byte[]) this.handler.getInputStream().readObject());
        } catch (ClassNotFoundException | IOException E) {
            println(E.toString());
        }
        return null;
    }

    /**
     * Helper method for the receive(); method, Converts byte[] into an object
     *
     * @param data byte[] that needs to be converted
     * @return This returns the converted Object
     * @throws IOException Thrown when Output Streams are unable to write the object to itself
     * @throws ClassNotFoundException Thrown when byte[] data is unable to be converted to an Object (byte[] is not serialized to an Object)
     */

    private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    /**
     * uses standard output and adds Server - and creates new line
     *
     * @param print outputted text
     */

    public void println(String print) {
        System.out.println("Server - " + print);
    }

    /**
     * uses standard output and adds Server -
     *
     * @param print outputted text
     */

    public void print(String print) {
        System.out.print("Server - " + print);
    }

    /**
     * Waits for inputted time
     *
     * @param time time to wait in milliseconds
     */

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException E) {
            System.out.println(E);
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
