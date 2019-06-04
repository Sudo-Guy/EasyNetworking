package EasyNetworking.library;

import java.io.*;

/**
 * <h1>ClientHandler - ClientHandler is a class that does not currently add any functionality to the default Handler Class</h1>
 *
 * <p>
 * ClientHandler is a class that does not currently add any functionality to the default Handler Class
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see Handler
 * @since 2019-05-20
 */
public class ClientHandler extends Handler {

    /**
     * This constructor builds a ObjectInputStream and ObjectOutputStream and then passes it to the super() constructor of the Handler Class
     *
     * @param out This is the OutputStream data required to create a ObjectOutputStream
     * @param in  This is the InputStream data required to create a ObjectInputStream
     * @throws IOException Throws When ObjectOutputStreams or ObjectOutputStream cannot be built
     * @see Handler
     * @see IOException
     */

    public ClientHandler(OutputStream out, InputStream in) throws IOException {
        super((new ObjectOutputStream(out)), new ObjectInputStream(in));
    }

    /**
     * This constructor calls the super(); constructor of Handler
     *
     * @see Handler
     */
    public ClientHandler() {
        super();
    }
}
