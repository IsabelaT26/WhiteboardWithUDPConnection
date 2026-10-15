import org.example.UdpConnection;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UdpTest {



    public static void main(String[] args) throws IOException {

        String host = "127.0.0.1";
        int peerPort = 2000;
        int localPort = 2000;

        if (args.length >= 1) {
            host = args[0];
        }

        if (args.length >= 2) {
            peerPort = Integer.parseInt(args[1]);
        }

        if (args.length >= 3) {
            localPort = Integer.parseInt(args[2]);
        }

        System.out.println(
                "Listening on " + localPort
                        + ", sending to " + host + ":" + peerPort
        );

        UdpConnection connection = new UdpConnection(localPort, host, peerPort);

        Thread receiver = new Thread(() -> {
            try {
                while (true) {
                    String message = connection.receive();
                    System.out.println("Received: " + message);

                }
            } catch (IOException e) {
                System.out.println("Receiving stopped: " + e.getMessage());
            }
        });
        receiver.start();

        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            connection.send(input.nextLine());
        }

    }

}
