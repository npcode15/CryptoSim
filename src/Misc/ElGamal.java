package Misc;

import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;

public class ElGamal
{
    private static BigInteger p;
	private static BigInteger b;
	private static BigInteger secretKey;
	private static BigInteger c;


	public static void main(String[] args) throws IOException
    {
    	Random sc = new SecureRandom();
		p = BigInteger.probablePrime(64, sc);
		b = new BigInteger("3");

		int x="encryptionKey".hashCode();
		if(x<0)
			x=x*(-1);
		secretKey=new BigInteger(x+"");
		
		c = b.modPow(secretKey, p);
        //
        // public key calculation
        //
		
        System.out.println("secretKey = " + secretKey);
        p = BigInteger.probablePrime(64, sc);
        b = new BigInteger("3");
        c = b.modPow(secretKey, p);
        System.out.println("p = " + p);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        //
        // Encryption
        //
        System.out.print("Enter your Big Number message -->\n");

        BigInteger X = new BigInteger("1235465656564");
        BigInteger r = new BigInteger(64, sc);
        BigInteger EC = X.multiply(c.modPow(r, p)).mod(p);
        BigInteger brmodp = b.modPow(r, p);
        System.out.println("Plaintext = " + X);
        System.out.println("r = " + r);
        System.out.println("EC = " + EC);
        System.out.println("b^r mod p = " + brmodp);
        //
        // Decryption 4323725980092828752
        //
        BigInteger crmodp = brmodp.modPow(secretKey, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(EC).mod(p);
        System.out.println("\n\nc^r mod p = " + crmodp);
        System.out.println("d = " + d);
        System.out.println("Alice decodes: " + ad);
    }
}
/*
import gnu.crypto.util.Util;

import java.security.Key; 
import java.security.KeyPair; 
import java.security.KeyPairGenerator; 
import java.security.SecureRandom; 
import javax.crypto.Cipher;  

/** * El Gamal example with random key generation. */ 
/*
public class ElGamal 
{ 
	public static void main(String[] args) throws Exception 
	{ 
		byte[] input = new byte[] { (byte)0xbe, (byte)0xef }; 
		Cipher cipher = Cipher.getInstance( "ElGamal/None/NoPadding", "BC"); 
		KeyPairGenerator generator = KeyPairGenerator.getInstance("ElGamal", "BC"); 
		SecureRandom random = new SecureRandom(); 

		// create the keys  generator.initialize(256, random);  
		KeyPair pair = generator.generateKeyPair();
		Key pubKey = pair.getPublic(); 
		Key privKey = pair.getPrivate();  
		System.out.println("input : " + Utils.toHex(input));  

		// encryption step 
		cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);  
		byte[] cipherText = cipher.doFinal(input);  
		System.out.println("cipher: " + Utils.toHex(cipherText));  

		// decryption step  
		cipher.init(Cipher.DECRYPT_MODE, privKey); 
		byte[] plainText = cipher.doFinal(cipherText);  
		System.out.println("plain : " + Utils.toHex(plainText)); 
	} 
}
}
}*/