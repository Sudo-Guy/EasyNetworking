package EasyNetworking.library;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>EasyMultiThreadedServer - EasyMultiThreadedServer simplify the creation of multi threaded servers allowing multiple clients connect to a single server</h1>
 *
 * <p>
 * EasyMultiThreadedServer simplify the creation of multi threaded servers allowing multiple clients connect to a single server
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see EasyServerSocket
 * @see ServerHandler
 * @since 2019-05-20
 */

public abstract class EasyMultiThreadedServer extends EasyServerSocket {

    /**
     * This ArrayList stores the reference points of the WorkThread Object that would be createdw
     */

    private ArrayList<Thread> workerThreads;

    /**
     * This constructor calls the super(port); constructor of the EasyServerSocket Class and then sets the default timeout and then instantiates the workThreads ArrayList
     * @param port This is where the Server will be located on the network
     * @throws IOException This is due to the extension to EasyServerSocket
     * @see EasyServerSocket
     */

    public EasyMultiThreadedServer(int port) throws IOException {
        super(port);
        try {

            this.setSoTimeout(10000);
            workerThreads = new ArrayList<>();

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This constructor calls the super(port); constructor of the EasyServerSocket Class and then sets the default timeout and then instantiates the workThreads ArrayList
     *
     * @param port       This is where the Server will be located on the network
     * @param serverName look at EasyServerSocket serverName documentation
     * @throws IOException This is due to the extension to EasyServerSocket
     * @see EasyServerSocket
     */

    public EasyMultiThreadedServer(int port, String serverName) throws IOException {
        super(port, serverName);
        try {

            this.setSoTimeout(10000);
            workerThreads = new ArrayList<>();

        } catch (IOException E) {
            println(E.toString());
        }
    }


    /**
     * This method allows ONE Client to connect to the server.
     * This method creates a new WorkerThread for the client and passes through a ServerHandler
     * @param printOut Determines if any of the Server connection info will be printed out
     */

    @Override
    public void accept(boolean printOut) {
        try {
            if (printOut)
                println(this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());
            handler = new ServerHandler(this.accept());
            if (printOut)
                println("Just connected to " + handler.getClient().getRemoteSocketAddress());

            Thread worker = new WorkerThread(handler);

            workerThreads.add(worker);
            worker.start();

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method allows ONE Client to connect to the server.
     * This method creates a new WorkerThread for the client and passes through a ServerHandler, also modifies the default Timeout
     * @param printOut Determines if any of the Server connection info will be printed out
     * @param timeout This determines the number of millisecond that the Server will wait for a client before creating a new instance
     */

    @Override
    public void accept(int timeout, boolean printOut) {
        try {
            this.setSoTimeout(timeout);
            if (printOut)
                println(this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());
            handler = new ServerHandler(this.accept());
            if (printOut)
                println("Just connected to " + handler.getClient().getRemoteSocketAddress());

            Thread worker = new WorkerThread(handler);

            workerThreads.add(worker);
            worker.start();

        } catch (IOException E) {
            println(E.toString());
        }
    }

    /**
     * This method allows you to get the workerThreads ArrayList
     * @return This returns the workerThreads ArrayList();
     */

    public ArrayList<Thread> getWorkerThreads() {
        return workerThreads;
    }

    /**
     * This method has to implemented by the user when instantiated an EasyMultiThreadServer.
     * This method is the Server code that will run when a client connects to the Server
     */

    public abstract void workerThreadImplementation(ServerHandler handler);

    /**
     * <h1>EasyMultiThreadedServer - EasyMultiThreadedServer.WorkerThread is used to run the Server code independently from the connection program so that multiple clients can connect at the same time </h1>
     *
     * <p>
     * EasyMultiThreadedServer.WorkerThread is used to run the Server code independently from the connection program using Threads, so that multiple clients can connect at the same time
     * </p>
     *
     * @author Hitesh Ale
     * @version 0.1
     * @see EasyMultiThreadedServer
     * @see Thread
     * @since 2019-05-20
     */

    public class WorkerThread extends Thread {

        /**
         * This ServerHandler contains all the information required to send and receive data from a Client independently from the ServerSocket.
         * TIP - When Creating a EasyMultiThreadServer use local handler methods Ex - handler.send(Object object); instead of send(Object object);
         * @see ServerHandler
         */

        private ServerHandler handler;

        /**
         * This Constructor stores the ServerHandler Object
         * @param handler This object is used to communicate with the Client
         */

        public WorkerThread(ServerHandler handler) {
            this.handler = handler;
        }

        /**
         * This method will auto-run and run the Server code implemented in workerThreadImplementation();
         */

        @Override
        public void run() {
            workerThreadImplementation(this.handler);
        }
    }

}
