package EasyNetworking.tests;

import EasyNetworking.library.EasyClientSocket;
import EasyNetworking.library.EasyServerSocket;

import java.io.IOException;
import java.util.Scanner;

public class TextingApp {

    private EasyClientSocket clientSocket;
    private EasyServerSocket serverSocket;

    public static void main(String[] args) {
        TextingApp c = new TextingApp();
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
            Client client = c.new Client(host, port, a.nextLine());
            client.start();
        } else {
            System.out.println("Please enter poet number: ");
            int port = a.nextInt();
            System.out.println("Please enter username: ");
            a.nextLine();
            Server server = c.new Server(port, a.nextLine());
            server.start();
        }
    }

    public class Client {
        String username;

        Client(String host, int port, String username) {
            clientSocket = new EasyClientSocket();
            clientSocket.connect(host, port);
            this.username = username;
        }

        public void start() {
            Thread incoming = new Incoming(clientSocket);
            Thread outgoing = new OutGoing(clientSocket);
            incoming.start();
            outgoing.start();
        }

        public class Incoming extends Thread {
            EasyClientSocket in;

            Incoming(EasyClientSocket in) {
                this.in = in;
            }

            public void run() {
                while (true) {
                    System.out.println(in.receive());
                }
            }
        }

        public class OutGoing extends Thread {
            EasyClientSocket out;

            OutGoing(EasyClientSocket out) {
                this.out = out;
            }

            public void run() {
                Scanner in = new Scanner(System.in);
                while (true) {
                    out.send(username + " - " + in.nextLine());
                }
            }
        }
    }

    class Server {
        String username;

        public Server(int port, String username) {
            try {
                serverSocket = new EasyServerSocket(port);
            } catch (IOException E) {

            }
            serverSocket.accept(100000, true);
            this.username = username;
        }

        void start() {
            Thread incoming = new Incoming(serverSocket);
            Thread outgoing = new OutGoing(serverSocket);
            incoming.start();
            outgoing.start();
        }

        class Incoming extends Thread {
            EasyServerSocket in;

            public Incoming(EasyServerSocket in) {
                this.in = in;
            }

            public void run() {
                while (true) {
                    System.out.println(in.receive());
                }
            }
        }

        class OutGoing extends Thread {
            EasyServerSocket out;

            OutGoing(EasyServerSocket out) {
                this.out = out;
            }

            public void run() {
                Scanner in = new Scanner(System.in);
                while (true) {
                    out.send(username + " - " + in.nextLine());
                }
            }
        }
    }
}
