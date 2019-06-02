package EasyNetworking.library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class EasyMultiThreadedServer extends EasyServerSocket {
    //workerThreads ArrayList is used to access active threads and their data, (client Socket, ObjectStreams)
    private ArrayList<Thread> workerThreads;

    /**
     * EasyMultiThreadedServer was made to make your life easy
     * to create a EasyMultiThreadedServer with default port number (3000)
     * EasyMultiThreadedServer nameOfVariable = new EasyMultiThreadedServer()
     * <p>
     * to create a EasyMultiThreadedServer with custom port number
     * EasyMultiThreadedServer nameOfVariable = new EasyMultiThreadedServer(int port)
     */

    public EasyMultiThreadedServer(int port) throws IOException {
        super(port);
        try {

            this.setSoTimeout(10000);
            workerThreads = new ArrayList<>();

        } catch (IOException E) {
            System.out.println(E);
        }
    }


    /**
     * acceptConnection allows a client to connect to the server
     * run this method once before attempting a connection using a client socket
     * example
     * nameOfVariable.acceptConnection();
     * to modify the amount of time the server waits for a connection (milliseconds)
     * nameOfVariable.acceptConnection(int timeout);
     * if "Server may already be connected to client" is printed please check any other instances of the acceptConnection method being used
     * Unlike EasyServerSocket a new Server does not have to be declared with each client allowing you to run this method multiple times without errors
     */

    @Override
    public void accept(boolean printOut) {
        try {
            handler = new ServerHandler();
            this.setSoTimeout(10000);
            println("Server - " + this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());
            handler.setClient(this.accept());
            println("Just connected to " + handler.getClient().getRemoteSocketAddress());

            handler.setInputStream(new ObjectInputStream(handler.getClient().getInputStream()));
            handler.setOutputStream(new ObjectOutputStream(handler.getClient().getOutputStream()));

            Thread worker = new WorkerThread(handler) {
                @Override
                public void run() {
                    workerThreadImplementation();
                }
            };

            workerThreads.add(worker);
            worker.start();

        } catch (IOException E) {
            System.out.println(E);
        }
    }

    @Override
    public void accept(int timeout, boolean printOut) {
        try {
            handler = new ServerHandler();
            this.setSoTimeout(timeout);
            println("Server - " + this.getInetAddress().getHostName() + " is waiting on " + this.getLocalPort() + "\nIP address: " + this.getInetAddress().getHostAddress());
            handler.setClient(this.accept());
            println("Just connected to " + handler.getClient().getRemoteSocketAddress());

            handler.setInputStream(new ObjectInputStream(handler.getClient().getInputStream()));
            handler.setOutputStream(new ObjectOutputStream(handler.getClient().getOutputStream()));

            Thread worker = new WorkerThread(handler) {
                @Override
                public void run() {
                    workerThreadImplementation();
                }
            };

            workerThreads.add(worker);
            worker.start();

        } catch (IOException E) {
            System.out.println(E);
        }
    }

    public Thread getWorker(int index) {
        return workerThreads.get(index);
    }

    public ArrayList<Thread> getWorkerThreads() {
        return workerThreads;
    }

    public abstract void workerThreadImplementation();

    public class WorkerThread extends Thread {

        private ServerHandler handler;

        public WorkerThread(ServerHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            workerThreadImplementation();
        }
    }

}
