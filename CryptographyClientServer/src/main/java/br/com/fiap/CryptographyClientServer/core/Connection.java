package br.com.fiap.CryptographyClientServer.core;

import br.com.fiap.CryptographyClientServer.CryptographyClientServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

public class Connection {
    public static String receive(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();

        byte[] infoBytes = new byte[256];
        int readBytes = in.read(infoBytes);

        if (readBytes == 0)
            return "";

        byte[] realBytes = Arrays.copyOf(infoBytes, readBytes);
        return Base64.getEncoder().encodeToString(realBytes);
    }

    public static PublicKey receiveKey(Socket socket) throws Exception {
        InputStream in = socket.getInputStream();

        byte[] infoBytes = new byte[2048];
        int readBytes = in.read(infoBytes);

        if (readBytes == 0)
            return null;

        byte[] realBytes = Arrays.copyOf(infoBytes, readBytes);
        return CryptographyClientServer.bytesToKey(realBytes);
    }

    public static void send(Socket socket, String content) throws IOException {
        byte[] requestBytes = Base64.getDecoder().decode(content);
        OutputStream out = socket.getOutputStream();
        out.write(requestBytes);
        out.flush();
    }

    public static void sendKey(Socket socket, PublicKey key) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(key.getEncoded());
        out.flush();
    }
}
