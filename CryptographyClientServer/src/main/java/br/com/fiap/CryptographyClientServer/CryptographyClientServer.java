package br.com.fiap.CryptographyClientServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@SpringBootApplication
public class CryptographyClientServer {

	public static KeyPair generatePublicPrivateKeys() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		return keyPairGenerator.generateKeyPair();
	}

	public static String encrypt(
			String message,
			PublicKey publicKey
	) throws Exception {
		byte[] messageBytes = message.getBytes();

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipherBytes = cipher.doFinal(messageBytes);
		return Base64.getEncoder().encodeToString(cipherBytes);
	}

	public static String decrypt(
			String message,
			PrivateKey privateKey
	) throws Exception {
		byte[] messageBytes = Base64.getDecoder().decode(message);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] decryptedMessage = cipher.doFinal(messageBytes);
		return new String(decryptedMessage, StandardCharsets.UTF_8);
	}

	public static PublicKey bytesToKey(
			byte[] bytesKey
	) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}
}
