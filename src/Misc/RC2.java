package Misc;

import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class RC2 
{
	public static String encrypt(byte[] key, byte[] iv, String unencrypted) throws NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
	IllegalBlockSizeException, BadPaddingException, java.security.InvalidKeyException, NoSuchProviderException
	{
		RC2ParameterSpec ivSpec = new RC2ParameterSpec(key.length*8, iv);
		Cipher cipher = Cipher.getInstance("RC2/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "RC2"), ivSpec);
		byte[] encrypted = cipher.doFinal(unencrypted.getBytes());
		return DatatypeConverter.printBase64Binary(encrypted);
	}

	public static String decrypt(byte[] key, byte[] iv, String encrypted) throws NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
	IllegalBlockSizeException, BadPaddingException, java.security.InvalidKeyException, NoSuchProviderException
	{//3fUuf6nZVBvLIfRrSLiq7A== cgdhrmdzY0iKx7O55/VqbQ==
		RC2ParameterSpec ivSpec = new RC2ParameterSpec(key.length*8, iv);
		Cipher cipher = Cipher.getInstance("RC2/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "RC2"), ivSpec);
		byte[] decrypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));

		return new String(decrypted);
	}
	
	public static void main(String []args ) throws java.security.InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException
	{
		String c=encrypt("HelloWORLD".getBytes(), "AAAAAAAAAAAAAAAA".getBytes(), "INDIAWORLD");
		String d=decrypt("HelloWORLD".getBytes(), "AAAAAAAAAAAAAAAA".getBytes(), c);
		System.out.println("Encrypted Text : "+c);
		System.out.println("Decrypted Text : "+d);
	}

}
