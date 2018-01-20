package Misc;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA 
{
	private static BigInteger n;

	private static BigInteger d;

	private static BigInteger e;

	private static int bitlen = 1024;

	/** Create an instance that can encrypt using someone else's public key. */
	public RSA(BigInteger newn, BigInteger newe)
	{
		n = newn;
		e = newe;
	}

	/** Create an instance that can both encrypt and decrypt. */
	public RSA(int bits) 
	{
		bitlen = bits;
		/*SecureRandom r = new SecureRandom();
		BigInteger p = new BigInteger(bitlen / 2, 100, r);
		BigInteger q = new BigInteger(bitlen / 2, 100, r);
		n = p.multiply(q);
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("3");
		while (m.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}
		d = e.modInverse(m);
		System.out.println("d : "+d);*/
	}

	/** Encrypt the given plaintext message. */
	public synchronized static String encrypt(String message) 
	{
		return (new BigInteger(message.getBytes())).modPow(e, n).toString();
	}

	/** Encrypt the given plaintext message. */
	public synchronized BigInteger encrypt(BigInteger message) 
	{
		return message.modPow(e, n);
	}

	/** Decrypt the given ciphertext message. */
	public synchronized static String decrypt(String message) 
	{
		return new String((new BigInteger(message)).modPow(d, n).toByteArray());
	}

	/** Decrypt the given ciphertext message. */
	public synchronized static BigInteger decrypt(BigInteger message)
	{
		return message.modPow(d, n);
	}

	/** Generate a new public and private key set. */
	public synchronized void generateKeys() 
	{
		SecureRandom r = new SecureRandom();
		BigInteger p = new BigInteger(bitlen / 2, 100, r);
		BigInteger q = new BigInteger(bitlen / 2, 100, r);
		n = p.multiply(q);
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("3");
		while (m.gcd(e).intValue() > 1)
		{
			e = e.add(new BigInteger("2"));
		}
		d = e.modInverse(m);
		
	}

	/** Return the modulus. */
	public synchronized BigInteger getN() 
	{
		return n;
	}

	/** Return the public key. */
	public synchronized BigInteger getE() 
	{
		return e;
	}

	/** Trivial test program. */
	public static void main(String[] args) 
	{
		RSA rsa = new RSA(1024);

		/*String text1 = "Yellow and Black Border Collies";
		System.out.println("Plaintext: " + text1);
		BigInteger plaintext = new BigInteger(text1.getBytes());

		BigInteger ciphertext = rsa.encrypt(plaintext);
		System.out.println("Ciphertext: " + ciphertext);
		plaintext = rsa.decrypt(ciphertext);

		String text2 = new String(plaintext.toByteArray());
		System.out.println("Plaintext: " + text2);*/
		
		SecureRandom r = new SecureRandom();
		BigInteger p = new BigInteger(bitlen / 2, 100, r);
		BigInteger q = new BigInteger(bitlen / 2, 100, r);
		n = p.multiply(q);
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		
		System.out.println("STEP 1 DONE ");
		
		/*int x="11256466".hashCode();
		int r1=0;
		int sum=0;
		if(x<0)
		  x=x*(-1);
		
		while(x>0)
			{
			 r1=x%10;
			 sum+=r1;
			 x=x/10;
			}
		while(sum>0)
		{
		 r1=sum%10;
		 x+=r1;
		 sum=sum/10;
		}
*/
		e = new BigInteger(7+"");
		System.out.println("STEP 2 DONE ");
		System.out.println(e);
		while(m.gcd(e).intValue() > 1)
			e = e.add(new BigInteger("2"));
		System.out.println("STEP 3 DONE ");
		BigInteger d = e.modInverse(m);
		System.out.println("STEP 4 DONE ");
		System.out.println("PRIVATE KEY : "+d);
		System.out.println("n : "+n);
		
		String cipherText=encrypt("Yellow and Black Border Collies");
		System.out.println("Plain : Yellow and Black Border Collies");
		
		System.out.println("Cipher : "+cipherText);
		String xyz=cipherText+"-"+n;
		String arr[]=xyz.split("-");
		n=new BigInteger(arr[1]);
		System.out.println("n : "+arr[1]);
		String pt=decrypt(arr[0]);
		System.out.println("Plain : "+pt);
	}
}

