package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Whiteboard{
    public static void main(String[] args) throws SocketException, UnknownHostException {

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

        UdpConnection connection = new UdpConnection(localPort, host, peerPort);


        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Whiteboard");

            DrawingPanel panel = new DrawingPanel(connection);

            window.add(panel);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);

            Thread receiver = new Thread(() -> {
                try {
                    while (true) {
                        String message = connection.receive();

                        // Split the message and convert its four coordinates.
                        String[] coordinates = message.split(" ");
                        int x1 = Integer.parseInt(coordinates[0]);
                        int y1 = Integer.parseInt(coordinates[1]);
                        int x2 = Integer.parseInt(coordinates[2]);
                        int y2 = Integer.parseInt(coordinates[3]);


                        SwingUtilities.invokeLater(() -> {
                            // Call panel.addLine(...) with those coordinates.
                            panel.addLine(x1,y1,x2,y2);
                        });
                    }
                } catch (IOException e) {
                    System.out.println("Receiving stopped: " + e.getMessage());
                }
            });
            receiver.start();
        });
    }

}
