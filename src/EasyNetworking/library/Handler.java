package EasyNetworking.library;

import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * <h1>Handler - Handler is a class facilitates the send and receiving of data from an ObjectInputStream and ObjectOutputStream</h1>
 *
 * <p>
 * Handler is a class facilitates the send and receiving of data from an ObjectInputStream and ObjectOutputStream
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutputStream
 * @since 2019-05-20
 */

public class Handler {

    /**
     * Variable in Contains an ObjectInputSteam that can be used by either EasyServerSocket or EasyClientSocket
     *
     * @see ObjectInputStream
     */

    protected ObjectInputStream in;

    /**
     * Variable in Contains an ObjectOutputSteam that can be used by either EasyServerSocket or EasyClientSocket
     * @see ObjectOutputStream
     */

    protected ObjectOutputStream out;


    /**
     * This Constructor requires no parameters, but to later use this Object InputStream and OutputStream must be set
     */

    protected Handler() {
    }

    /**
     * @param out Requires ObjectOutputStream
     * @param in Requires ObjectInputStream
     */

    public Handler(ObjectOutputStream out, ObjectInputStream in) {
        this.in = in;
        this.out = out;
    }

    /**
     * This method allows you to access the ObjectInputStream
     * @return stored ObjectInputStream
     */

    /**
     * This method allows you to send any objects to your Client or Server
     *
     * @param object This is the object being sent to your Client or Server
     * @throws IOException Thrown when Output Stream is unable to write the object to itself
     */

    public void send(Object object) {
        if (out == null) {
            System.out.println("ObjectOutputStream is null at " + this.getClass());
        }
        try {
            this.out.writeObject(serialize(object));
        } catch (IOException E) {
            System.out.println(this.getClass() + "  " + E.toString());
            E.printStackTrace();
        }
    }

    /**
     * Helper method for the send(); function, turns Objects into byte[]
     *
     * @param obj This is the object being serialized
     * @return serialized Object as a byte[]
     * @throws IOException Thrown when Output Streams are unable to write the object to itself
     */

    protected byte[] serialize(@NotNull Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /**
     * This method waits until a Object is sent by a Client or Server
     *
     * @return This returns the Object sent by the Client or Server
     * @throws IOException Thrown when Input Streams are unable to write the object to itself
     */

    public Object receive() {
        if (in == null) {
            System.out.println("ObjectInputStream is null at " + this.getClass());
        }
        try {
            return deserialize((byte[]) in.readObject());
        } catch (ClassNotFoundException | IOException E) {
            System.out.println(this.getClass() + "  " + E.toString());
        }
        return null;
    }

    /**
     * Helper method for the receive(); method, Converts byte[] into an object
     *
     * @param data byte[] that needs to be converted
     * @return This returns the converted Object
     * @throws IOException            Thrown when Output Streams are unable to write the object to itself
     * @throws ClassNotFoundException Thrown when byte[] data is unable to be converted to an Object (byte[] is not serialized to an Object)
     */

    protected Object deserialize(@NotNull byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public ObjectInputStream getInputStream() {
        return in;
    }

    /**
     * Allows you to set current ObjectInputStream
     * @param in applicable InputStream
     */

    protected void setInputStream(ObjectInputStream in) {
        this.in = in;
    }

    /**
     * This method allows you to access the ObjectOutputStream
     * @return stored ObjectOutputStream
     */

    public ObjectOutputStream getOutputStream() {
        return out;
    }

    /**
     * Allows you to set current ObjectOutputStream
     * @param out applicable OutputStream
     */

    protected void setOutputStream(ObjectOutputStream out) {
        this.out = out;
    }
}
