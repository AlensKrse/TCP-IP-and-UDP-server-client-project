package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Main {

    public static final int PORT = 5001;
    public static final int BUFFER_SIZE = 50;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                final byte[] buffer = new byte[BUFFER_SIZE];
                final DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);
                final String result = new String(buffer, 0, datagramPacket.getLength());
                System.out.println("Text received is: " + result);

                final String returnText = "echo: " + result;
                final byte[] returnTextBytes = returnText.getBytes(StandardCharsets.UTF_8);
                final InetAddress address = datagramPacket.getAddress();
                final int port = datagramPacket.getPort();
                final DatagramPacket returnPacket = new DatagramPacket(returnTextBytes, returnTextBytes.length, address, port);
                socket.send(returnPacket);
            }
        } catch (final IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
