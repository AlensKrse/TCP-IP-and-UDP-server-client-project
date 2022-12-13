package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static final int PORT = 5001;
    public static final String EXIT_MESSAGE = "exit";
    public static final int BUFFER_SIZE = 50;

    public static void main(String[] args) {
        try (final DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getLocalHost();

            final Scanner scanner = new Scanner(System.in);
            String line;
            do {
                System.out.println("Enter text:");
                line = scanner.nextLine();

                final byte[] buffer = line.getBytes(StandardCharsets.UTF_8);
                final DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, address, PORT);
                socket.send(datagramPacket);

                final byte[] receiveBuffer = new byte[BUFFER_SIZE];
                final DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                System.out.println("Text receive: " + new String(receiveBuffer, 0, receivePacket.getLength()));
            } while (!line.equalsIgnoreCase(EXIT_MESSAGE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
