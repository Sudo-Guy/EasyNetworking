package EasyNetworking;

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
 * @version 1.0
 * @see java.net.ServerSocket
 * @since 2019-05-20
 */

public class EasyServerSocket extends ServerSocket {

    /**
     * Hold
     * @see ServerHandler
     */
    protected ServerHandler handler;


    /**
     * calls the Constructor in ServerSocket while also setting a default timeout of 10000 milliseconds
     *
     * @param port port number where EasyServerSocket will be created at
     * @throws IOException N/A
     * @see IOException
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
     * This method will allow the Server to allow clients to connect to itself
     *
     * @param printOut Determines if program will print out Server and Connected Client details
     */

    public void accept(boolean printOut) {
        if (handler.getClient() != null) {
            if (printOut)
                System.out.println("Server may already be connected to client");
        }
        handler = new ServerHandler();
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
     * This method will allow the Server to allow clients to connect to itself
     *
     * @param timeout  Will determine how long the server will wait for a connection in milliseconds
     * @param printOut Determines if program will print out Server and Connected Client details
     */

    public void accept(int timeout, boolean printOut) {
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
     * This method allows you to send any objects to your connected client
     *
     * @param object This is the object being sent to your Client
     */

    public void send(Object object) {
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
     * @throws IOException
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
     */

    public Object receive() {
        try {
            return deserialize((byte[]) this.handler.getInputStream().readObject());
        } catch (ClassNotFoundException | IOException E) {
            println(E.toString());
        }
        return null;
    }

    /**
     * Deserializes a byte[]
     *
     * @param data byte[] that needs to be converted
     * @return This returns the converted Object
     * @throws IOException            N/A
     * @throws ClassNotFoundException N/A
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
     * Used to return handler
     *
     * @return ServerHandler
     */

    public ServerHandler getHandler() {
        return handler;
    }

}
