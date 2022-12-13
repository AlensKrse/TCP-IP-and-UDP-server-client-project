package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static final String HOST = "localhost";
    public static final String EXIT = "exit";
    public static final int TIMEOUT = 5000;
    public static final int PORT = 5001;

    public static void main(String[] args) {
        try (final Socket socket = new Socket(HOST, PORT)) {
            socket.setSoTimeout(TIMEOUT);
            final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            final Scanner scanner = new Scanner(System.in);

            String userInput;
            do {
                System.out.println("Enter text to be echoed: ");
                userInput = scanner.nextLine();
                output.println(userInput);
                if (!userInput.equalsIgnoreCase(EXIT)) {
                    final String line = input.readLine();
                    System.out.println(line);
                }
            } while (!userInput.equalsIgnoreCase(EXIT));
        } catch (final IOException e) {
            System.out.println("Client error: " + e.getLocalizedMessage());
            throw new UnsupportedOperationException(e);
        }
    }
}
