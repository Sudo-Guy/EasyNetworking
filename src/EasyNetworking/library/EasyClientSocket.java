package EasyNetworking;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Hitesh Ale
 * Date - 5 / 20 / 2019
 * @version 1.0
 */

public class EasyClientSocket extends Socket {

    protected ClientHandler handler;


    /**
     * EasyClientSocket was made to make your life easy
     * to create a EasyClientSocket
     * EasyClientSocket nameOfVariable = new EasyClientSocket();
     */

    public EasyClientSocket() {
        super();
    }


    public void connect(String hostname, int port) {
        try {
            println("Connecting...");
            super.connect(new InetSocketAddress(hostname, port));
            println("Connected to " + this.getInetAddress().getHostName());

            handler.setOutputStream(new ObjectOutputStream(this.getOutputStream()));
            handler.setInputStream(new ObjectInputStream(this.getInputStream()));

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * The send() method allows you to send anything to your client expect primitive variables
     * primitives have to be converted to their Object counterparts
     * char --> Character
     * int --> Integer
     * double --> Double
     * boolean --> Boolean
     * <p>
     * Casting example
     * int a = 100;
     * nameOfVariable.send((Integer) a);
     */

    public int send(Object object) {
        try {
            handler.getOutputStream().writeObject(serialize(object));
            return 0;
        } catch (IOException E) {
            System.out.println(E);
            return -1;
        }
    }

    /**
     * Helper method serialize allows Object to turn into byte[]
     * This allows the method to send any Object to the client
     * example
     * Buffered Images can usually not be sent through a stream but this method converts all data to byte[] allowing it to be transferable
     */

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /**
     * The receive() method waits until the client has sent an Object
     * if the method fails, null will be return
     * <p>
     * Note this method returns an object, Thus after returning the Object you will have to cast it to another type
     * example
     * (Assume client has sent a String)
     * String message = (String) nameOfVariable.receive();
     * DO NOT
     * String message = nameOfVariable.receive();
     * // this will cause an error
     */

    public Object receive() {
        try {
            return deserialize((byte[]) handler.getInputStream().readObject());
        } catch (ClassNotFoundException | IOException E) {
            System.out.println(E);
        }
        return null;
    }

    /**
     * Helper method deserialize allows byte[] turn into Object
     * This allows the method to receive any byte[] to the client
     * example
     * Buffered Images can usually not be received through a stream but this method converts byte[] to Object allowing it to be transferable
     */

    private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    private void println(String print) {
        System.out.println("Client - " + print);
    }

    private void print(String print) {
        System.out.print("Client - " + print);
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException E) {
            System.out.println(E);
        }
    }

    /**
     * These methods allow you to access the internal methods of java.net.Socket & java.io.*
     */

    public ClientHandler getHandler() {
        return handler;
    }
}
