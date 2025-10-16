package br.com.fiap.CryptographyClientServer.core;

import br.com.fiap.CryptographyClientServer.CryptographyClientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Scanner;

public class Server {

    Socket socketClient;
    ServerSocket serverSocket;

    public boolean connect() {
        try {
            socketClient = serverSocket.accept();
            return true;
        } catch (IOException e) {
            System.out.println("Cannot connect to server: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Server server = new Server();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws Exception {
        String receivedText = "";
        String sentText = "Olá Client";
        String decryptedText;
        String encryptedText;

        serverSocket = new ServerSocket(9600);
        Scanner input = new Scanner(System.in);
        System.out.println("Server Initialized...");

        while (true) {
            if (connect()) {
                System.out.println("Generating RSA Key...");
                KeyPair keys = CryptographyClientServer.generatePublicPrivateKeys();

                System.out.println("Sending Public Key...");
                Connection.sendKey(socketClient, keys.getPublic());

                System.out.println("Receiving Public Key from Client...");
                PublicKey publicKey = Connection.receiveKey(socketClient);

                receivedText = Connection.receive(socketClient);
                System.out.println("\nReceived Message: " + receivedText);

                decryptedText = CryptographyClientServer.decrypt(receivedText, keys.getPrivate());
                System.out.println("Client Sent: " + decryptedText);
                System.out.println("Type a message: ");
                sentText = input.nextLine();

                encryptedText = CryptographyClientServer.encrypt(sentText, publicKey);
                System.out.println("Sent Text: " + sentText);

                Connection.send(socketClient, encryptedText);
                socketClient.close();
            }
        }
    }
}
