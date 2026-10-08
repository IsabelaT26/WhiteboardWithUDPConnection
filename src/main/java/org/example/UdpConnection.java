package org.example;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UdpConnection {

    private final DatagramSocket socket;
    private final InetAddress peerAddress;
    private final int peerPort;


    public UdpConnection(int localPort, String peerHost, int peerPort)
            throws SocketException, UnknownHostException {

        this.peerAddress = InetAddress.getByName(peerHost);
        this.socket = new DatagramSocket(localPort);
        this.peerPort = peerPort;
    }

    public void send(String message) throws IOException {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(data, data.length, peerAddress, peerPort);
        socket.send(packet);
    }

    public String receive() throws IOException {
        // 1. Create a byte array to hold incoming data.
        byte[] buffer = new byte[1024];
        // 2. Create a DatagramPacket using that array.
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        // 3. Receive a packet through socket.
        socket.receive(packet);
        // 4. Decode its contents as UTF-8 and return the text.
        String message = new String(
                packet.getData(),
                packet.getOffset(),
                packet.getLength(),
                StandardCharsets.UTF_8
        );
        return message;
    }
}
