package EasyNetworking.library;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h1>Handler</h1>
 *
 * <p>
 * Handler is a class that contain Input and Output
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
     * This method allows you to acess the ObjectInputStream
     * @return stored ObjectInputStream
     */

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
