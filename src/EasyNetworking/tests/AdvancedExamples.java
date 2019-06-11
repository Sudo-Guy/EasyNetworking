package EasyNetworking.tests;

import EasyNetworking.library.EasyClientSocket;
import EasyNetworking.library.EasyMultiThreadedServer;
import EasyNetworking.library.ServerHandler;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

/**
 * <h1>AdvancedExamples - This class is a test scenario for EasyMultiThreadedServer to validate the integrity of the files and demonstrate a use case of EasyMultiThreadedServer</h1>
 *
 * <p>
 * This class is a test scenario for EasyMultiThreadedServer to validate the integrity of the files and demonstrate a use case of EasyMultiThreadedServer
 * </p>
 *
 * @author Hitesh Ale
 * @version 0.1
 * @see EasyMultiThreadedServer
 * @see Thread
 * @since 2019-05-20
 */

public class AdvancedExamples {

    /**
     * The main method will allow the user to wither launch a Client Socket or start a Server
     *
     * @param args This is unused
     */

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
            Thread client = c.new AdvancedExamplesClient(host, port);
            client.start();
        } else {
            System.out.println("Please enter port number: ");
            int port = a.nextInt();
            Thread server = c.new AdvancedExamplesServer(port);
            server.start();
        }
    }

    /**
     * <h1></h1>
     *
     * <p>
     * AdvancedExamplesClient
     * </p>
     *
     * @author Hitesh Ale
     * @version 0.1
     * @see EasyMultiThreadedServer
     * @see Thread
     * @since 2019-05-20
     */

    public class AdvancedExamplesClient extends Thread {
        private EasyClientSocket clientSocket;

        private String incoming;

        private String outgoing;


        public AdvancedExamplesClient(String hostname, int port) {
            clientSocket = new EasyClientSocket();
            clientSocket.connect(hostname, port);
            Incoming in = new Incoming(clientSocket);
            Outgoing out = new Outgoing(clientSocket);
            in.start();
            out.start();
        }

        public class Incoming extends Thread {
            private Vector<String> data;

            public EasyClientSocket clientSocket;

            public Incoming(EasyClientSocket clientSocket) {
                this.clientSocket = clientSocket;
                data = new Vector<>();
            }

            @Override
            public void run() {
                do {
                    incoming = (String) clientSocket.receive();
                    System.out.println(incoming);
                } while (incoming.contains("try again"));
                while (!outgoing.equalsIgnoreCase("/exit")) {
                    int currentLength = data.size();
                    data = (Vector) clientSocket.receive();
                    for (int i = currentLength; i < data.size(); i++) {
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println(data.get(i));
                    }
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
                Scanner in = new Scanner(System.in);
                do {
                    System.out.println("Please enter username: ");
                    String username = in.nextLine() + ",";
                    System.out.println("Please enter password: ");
                    username += in.nextLine() + ",";
                    clientSocket.send(username);
                    clientSocket.println(username);
                    clientSocket.sleep(3000);
                } while (incoming.contains("try again"));
                while (!outgoing.equalsIgnoreCase("/exit")) {
                    String sending = in.nextLine();
                    if (sending.contains("/exit")) {
                        clientSocket.send(sending);
                        clientSocket.sleep(100);
                        try {
                            clientSocket.close();
                        } catch (IOException E) {
                            clientSocket.println(E.toString());
                        }
                    }
                    clientSocket.send(sending);
                }
            }
        }
    }

    public class AdvancedExamplesServer extends Thread {

        public AdvancedExamplesServer(int port) {
            try {
                EasyMultiThreadedServer threadedServer = new EasyMultiThreadedServer(port) {
                    Vector<String> data = new Vector<>();
                    Vector<String[]> info = new Vector<>();

                    boolean dead = false;

                    @Override
                    public void workerThreadImplementation(ServerHandler handler) {
                        String username;
                        int checkUser;
                        do {
                            username = (String) handler.receive();
                            String[] bufferedUsername = username.split(",");
                            checkUser = checkUser(bufferedUsername);
                            if (checkUser == 0)
                                handler.send("Welcome back to the server");
                            else if (checkUser == 1) {
                                handler.send("Welcome to the server \nYou have been registered\nUsername: " + bufferedUsername[0] + "\nPassword: " + bufferedUsername[1]);
                                info.add(username.split(","));
                            } else if (checkUser == -1) {
                                handler.send("Incorrect Password try again");
                            }
                        } while (checkUser == -1);

                        username = username.split(",")[0];

                        Incoming in = new Incoming(username, handler);
                        Outgoing out = new Outgoing(handler);
                        in.start();
                        out.start();
                        while (handler.getClient().isConnected()) {
                            sleep(1);
                        }
                        dead = true;
                    }

                    private int checkUser(String[] username) {
                        for (String[] userInfo : info) {
                            if (userInfo[0].equalsIgnoreCase(username[0]))
                                if (userInfo[1].equalsIgnoreCase(username[1]))
                                    return 0;
                                else
                                    return -1;
                        }
                        return 1;
                    }

                    class Incoming extends Thread {
                        String username;
                        ServerHandler handler;

                        Incoming(String username, ServerHandler handler) {
                            this.username = username;
                            this.handler = handler;
                        }

                        @Override
                        public void run() {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            while (!dead) {
                                String received = (String) handler.receive();
                                if (received.contains("/exit"))
                                    try {
                                        handler.getClient().close();
                                    } catch (IOException E) {
                                        println(E.toString());
                                    }
                                Date date = new Date();
                                data.add(username + "(" + dateFormat.format(date) + ")" + " - " + received);
                                println(username + " - " + received);
                            }
                        }
                    }

                    class Outgoing extends Thread {
                        ServerHandler handler;

                        Outgoing(ServerHandler handler) {
                            this.handler = handler;
                        }

                        @Override
                        public void run() {
                            int size = data.size();
                            while (!dead) {
                                if (size != data.size()) {
                                    size = data.size();
                                    handler.send(data);
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
