package EasyNetworking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientHandler extends Handler {

    public ClientHandler(ObjectInputStream in, ObjectOutputStream out) {
        super(out, in);
    }
}
