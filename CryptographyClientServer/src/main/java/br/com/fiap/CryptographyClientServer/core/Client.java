package br.com.fiap.CryptographyClientServer.core;

import br.com.fiap.CryptographyClientServer.CryptographyClientServer;

import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Scanner;

public class Client {
    Socket socket;

    public void connectToServer() throws Exception {
        String requestText = "Connect Established...";

        socket = new Socket("127.0.0.1", 9600);

        System.out.println("Generating RSA Key...");
        KeyPair keys = CryptographyClientServer.generatePublicPrivateKeys();
        PublicKey publicKey = Connection.receiveKey(socket);

        System.out.println("Sending public key...");
        Connection.sendKey(socket, keys.getPublic());

        Scanner input = new Scanner(System.in);
        System.out.println("Type a message: ");
        requestText = input.nextLine();

        String encryptedText = CryptographyClientServer.encrypt(requestText, publicKey);

        System.out.println("\nEncrypted Message: " + encryptedText);
        Connection.send(socket, encryptedText);

        String receivedText = Connection.receive(socket);
        System.out.println("\nServer sent: " + receivedText);

        String decryptedText = CryptographyClientServer.decrypt(receivedText, keys.getPrivate());

        System.out.println("\nDecrypted Message: " + decryptedText);
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.connectToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
