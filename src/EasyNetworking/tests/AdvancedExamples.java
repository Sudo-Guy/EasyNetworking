package EasyNetworking.tests;

import EasyNetworking.library.EasyClientSocket;
import EasyNetworking.library.EasyMultiThreadedServer;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class AdvancedExamples {

    public static void main(String[] args) {
        AdvancedExamples c = new AdvancedExamples();
        Scanner a = new Scanner(System.in);
        System.out.println("Please write 1. client, or 2. server: ");
        if (a.nextInt() == 1) {
            System.out.println("Please enter host name: ");
            a.nextLine();
            String host = a.nextLine();
            System.out.println("Please enter port number: ");
            int port = a.nextInt();
            System.out.println("Please enter username: ");
            a.nextLine();
            Thread client = c.new AdvancedExamplesClient(host, port, a.nextLine());
            client.start();
        } else {
            System.out.println("Please enter poet number: ");
            int port = a.nextInt();
            Thread server = c.new AdvancedExamplesServer(port);
            server.start();
        }
    }

    public class AdvancedExamplesClient extends Thread {
        private EasyClientSocket clientSocket;
        private String username;

        public AdvancedExamplesClient(String hostname, int port, String username) {
            this.username = username;
            clientSocket = new EasyClientSocket();
            clientSocket.connect(hostname, port);
            Incoming in = new Incoming(clientSocket);
            Outgoing out = new Outgoing(clientSocket);
            in.start();
            out.start();
        }

        public class Incoming extends Thread {
            public EasyClientSocket clientSocket;

            public Incoming(EasyClientSocket clientSocket) {
                this.clientSocket = clientSocket;
            }

            @Override
            public void run() {
                while (true) {
                    System.out.println((String) clientSocket.receive());
                }
            }
        }

        public class Outgoing extends Thread {
            public EasyClientSocket clientSocket;

            public Outgoing(EasyClientSocket clientSocket) {
                this.clientSocket = clientSocket;
            }

            @Override
            public void run() {
                clientSocket.send(username);
                Scanner in = new Scanner(System.in);
                while (true) {
                    clientSocket.send(in.nextLine());
                }
            }
        }
    }

    public class AdvancedExamplesServer extends Thread {

        public AdvancedExamplesServer(int port) {
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
                        while (handler.getClient().isConnected()) {
                            sleep(1);
                        }
                        dead = true;
                    }

                    class Incoming extends Thread {
                        @Override
                        public void run() {
                            while (!dead) {
                                data.add((String) receive());
                                println(username);
                            }
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
