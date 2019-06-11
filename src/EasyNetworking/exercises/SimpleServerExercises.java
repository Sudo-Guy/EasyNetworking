package EasyNetworking.exercises;

import EasyNetworking.library.EasyClientSocket;
import EasyNetworking.library.EasyServerSocket;

import java.io.IOException;

public class SimpleServerExercises {

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


    public static void main(String[] args) {
        SimpleServerExercises practise = new SimpleServerExercises();
        Thread server = practise.new Server();
        Thread client = practise.new Client();
        server.start();
        client.start();
    }

    public class Server extends Thread {

        public void run() {
            //Server Code Goes Here
            try {
                EasyServerSocket socket = new EasyServerSocket(9999);

                socket.accept(15000, true);

                long currentTime = System.currentTimeMillis();

                int avgTime = 0;

                for (int i = 0; i < 100; i++) {
                    println((String) socket.receive());
                    avgTime += System.currentTimeMillis() - currentTime;
                    currentTime = System.currentTimeMillis();
                }
                avgTime /= 100;

                if (avgTime > 1100) {
                    println("The client is too fast");
                } else if (avgTime < 900) {
                    println("The client is too slow");
                } else {
                    println("The client is perfect");
                }
            } catch (IOException E) {
                println(E.toString());
            }
        }

        private void println(String print) {
            System.out.println("Server - " + print);
        }

        private void print(String print) {
            System.out.print("Server - " + print);
        }

        private void sleep(int time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException E) {
                System.out.println(E);
            }
        }
    }

    public class Client extends Thread {

        public void run() {
            sleep(500);
            //Client code Goes Here
            EasyClientSocket socket = new EasyClientSocket("Hitesh's Computer");

            socket.connect("0.0.0.0", 9999);

            for (int i = 0; i < 100; i++) {
                socket.send(socket.getClientName() + ", Hey here is a message");
                sleep(1000);
            }
        }

        private void println(String print) {
            System.out.println("Client - " + print);
        }

        private void print(String print) {
            System.out.print("Client - " + print);
        }

        private void sleep(int time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException E) {
                System.out.println(E);
            }
        }
    }


}
