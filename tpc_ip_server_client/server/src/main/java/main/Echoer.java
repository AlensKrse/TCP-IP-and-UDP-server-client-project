package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static main.Main.EXIT;

public class Echoer extends Thread {

    private final Socket socket;

    public Echoer(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                final String line = reader.readLine();
                if (line.equalsIgnoreCase(EXIT)) {
                    break;
                }
                writer.println("Echo from server: " + line);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        } finally {
            try {
                socket.close();
            } catch (final IOException e) {
                System.out.println("Socket closing problem: " + e.getMessage());
            }
        }
    }
}
