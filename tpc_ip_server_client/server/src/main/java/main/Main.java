package main;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static final int PORT = 5001;
    public static final String EXIT = "exit";

    public static void main(String[] args) {
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new Echoer(serverSocket.accept()).start();
            }
        } catch (final IOException e) {
            System.out.println("Server socker error: " + e.getLocalizedMessage());
            throw new UnsupportedOperationException(e);
        }
    }
}
