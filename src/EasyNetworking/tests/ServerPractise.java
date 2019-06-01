package EasyNetworking;

public class ServerPractise {

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
        ServerPractise practise = new ServerPractise();
        Thread server = practise.new Server();
        Thread client = practise.new Client();
        server.start();
        client.start();
    }

    public class Server extends Thread {

        public void run() {
            //Server Code Goes Here
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
            //Client Code Goes Here
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
