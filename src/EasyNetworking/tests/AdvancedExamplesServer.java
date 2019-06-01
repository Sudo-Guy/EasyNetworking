package EasyNetworking;

import java.io.IOException;
import java.util.Vector;

public class AdvancedExamplesServer {
    /**
     * Information -
     * Use the EasyServerSocket and EasyClient Classes to communicate between a client and a server
     * When ever .receive() is used .send must also be called or else the class using .receive() will pause indefinably
     * use .receive() before .send()
     * <p>
     * If you are using this provided environment to write your code using "Client - " or "Server - " to distinguish which program is outputting text
     * <p>
     * run .acceptConnection() before connect() or else an exception will be thrown
     */


    public class Server {

        public Server(int port) {
            try {
                EasyMultiThreadedServer threadedServer = new EasyMultiThreadedServer(port) {
                    Vector<String> data = new Vector<>();
                    String username;
                    boolean dead = false;

                    @Override
                    public void workerThreadImplementation() {
                        username = (String) receive();
                        send("Welcome to the server");
                        Incoming in = new Incoming();
                        Outgoing out = new Outgoing();
                        in.start();
                        out.start();
                    }

                    class Incoming extends Thread {
                        @Override
                        public void run() {
                            while (!dead)
                                data.add((String) receive());
                        }
                    }

                    class Outgoing extends Thread {
                        @Override
                        public void run() {
                            int size = data.size();
                            while (!dead) {
                                if (size != data.size()) {
                                    size = data.size();
                                    send(data);
                                }
                            }
                        }
                    }
                };
                do {
                    threadedServer.accept(100000, true);
                } while (threadedServer.getWorkerThreads().size() != 20);
            } catch (IOException E) {
                System.out.println(E);
            }
        }


    }
}
