package EasyNetworking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Handler {

    protected ObjectInputStream in;

    protected ObjectOutputStream out;

    protected Handler() {
    }

    public Handler(ObjectOutputStream out, ObjectInputStream in) {
        this.in = in;
        this.out = out;
    }

    protected ObjectInputStream getInputStream() {
        return in;
    }

    protected void setInputStream(ObjectInputStream in) {
        this.in = in;
    }

    protected ObjectOutputStream getOutputStream() {
        return out;
    }

    protected void setOutputStream(ObjectOutputStream out) {
        this.out = out;
    }
}
