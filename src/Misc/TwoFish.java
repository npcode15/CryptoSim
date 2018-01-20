package Misc;
import gnu.crypto.cipher.Twofish;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;

import org.apache.commons.codec.binary.Base64;

public class TwoFish {
public static void main(String argv[]) throws Exception {
String key = "1234567812345678";
String plaintext = "I am text to be hidden away";
String cipher = encrypt(plaintext,key);
System.out.println(cipher);
String cplaintext = decrypt(cipher,key);
System.out.println(cplaintext);
}

public static String encrypt (String cookieValue, String key) throws InvalidKeyException, UnsupportedEncodingException {
	byte[] plainText;
	byte[] encryptedText;
	Twofish twofish = new Twofish();
	// create a key
	byte[] keyBytes = key.getBytes();
	Object keyObject = twofish.makeKey(keyBytes, 16);
	//make the length of the text a multiple of the block size
	if ((cookieValue.length() % 16) != 0) {
		while ((cookieValue.length() % 16) != 0) {
			cookieValue += " ";
		}
	}
	// initialize byte arrays for plain/encrypted text
	plainText = cookieValue.getBytes("UTF8");
	encryptedText = new byte[cookieValue.length()];
	// encrypt text in 8-byte chunks
	for (int i=0; i<Array.getLength(plainText); i+=16) {
		twofish.encrypt(plainText, i, encryptedText, i, keyObject, 16);
	}
	String encryptedString = Base64.encodeBase64URLSafeString(encryptedText);
	return encryptedString;
}

public static String decrypt (String cookieValue, String key) throws InvalidKeyException, UnsupportedEncodingException 
{
byte[] encryptedText;
byte[] decryptedText;
Twofish twofish = new Twofish();
//create the key
byte[] keyBytes = key.getBytes();
Object keyObject = twofish.makeKey(keyBytes, 16);
//make the length of the string a multiple of
//the block size
if ((cookieValue.length() % 16) != 0) {
while ((cookieValue.length() % 16) != 0) {
cookieValue += " ";
}
}
//initialize byte arrays that will hold encrypted/decrypted
//text
encryptedText = Base64.decodeBase64(cookieValue);
decryptedText = new byte[cookieValue.length()];
//Iterate over the byte arrays by 16-byte blocks and decrypt.
for (int i=0; i<Array.getLength(encryptedText); i+=16) {
twofish.decrypt(encryptedText, i, decryptedText, i, keyObject, 16);
}
String decryptedString = new String(decryptedText, "UTF8");
return decryptedString;
}
}