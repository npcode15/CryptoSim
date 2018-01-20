package Misc;

/*import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDES {

	public static void main(String[] args) throws Exception {

		String text = "kyle boon";

		byte[] codedtext = new TripleDES().encrypt(text);
		String decodedtext = new TripleDES().decrypt(codedtext);

		System.out.println(codedtext); // this is a byte array, you'll just see a reference to an array
		System.out.println(decodedtext); // This correctly shows "kyle boon"
	}

	public byte[] encrypt(String message) throws Exception 
	{
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest("HG58YZ3CR9".getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) 
			keyBytes[k++] = keyBytes[j++];

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		final byte[] plainTextBytes = message.getBytes("utf-8");
		final byte[] cipherText = cipher.doFinal(plainTextBytes);
		// final String encodedCipherText = new sun.misc.BASE64Encoder()
		// .encode(cipherText);

		return cipherText;
	}

	public String decrypt(byte[] message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
				.getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, key, iv);

		// final byte[] encData = new
		// sun.misc.BASE64Decoder().decodeBuffer(message);
		final byte[] plainText = decipher.doFinal(message);

		return new String(plainText, "UTF-8");
	}
}*/

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class TripleDES {

	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec myKeySpec;
	private SecretKeyFactory mySecretKeyFactory;
	private Cipher cipher;
	byte[] keyAsBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	public TripleDES() throws Exception
	{
		myEncryptionKey = "ThisIsSecretEncryptionKey";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		myKeySpec = new DESedeKeySpec(keyAsBytes);
		mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = mySecretKeyFactory.generateSecret(myKeySpec);
	}

	/**
	 * Method To Encrypt The String
	 */
	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			Base64 base64encoder = new Base64();
			encryptedString = base64encoder.encodeToString(encryptedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}
	/**
	 * Method To Decrypt An Ecrypted String
	 */
	public String decrypt(String encryptedString) {
		String decryptedText=null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			Base64 base64decoder = new Base64();
			byte[] encryptedText = base64decoder.decode(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText= bytes2String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
	/**
	 * Returns String From An Array Of Bytes
	 */
	private static String bytes2String(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i<bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}

	/**
	 * Testing The DESede Encryption And Decryption Technique
	 */
	public static void main(String args []) throws Exception
	{
		TripleDES myEncryptor= new TripleDES();

		String stringToEncrypt="Classified Information!     ";
		String encrypted=myEncryptor.encrypt(stringToEncrypt);
		String decrypted=myEncryptor.decrypt(encrypted);

		System.out.println("String To Encrypt: "+stringToEncrypt);
		System.out.println("Encrypted Value :" + encrypted);
		System.out.println("Decrypted Value :"+decrypted);

	}

}