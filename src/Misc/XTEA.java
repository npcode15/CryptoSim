package Misc;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import org.apache.commons.codec.binary.Base64;

public class XTEA {

	/**** Constants ****/
	private static final int
	ROUNDS      = 64,   // iteration count (cycles)
	BLOCK_SIZE  = 8,    // bytes in a data block (64 bits)
	KEY_SIZE    = 16,   // key size (128 bits)
	DELTA       = 0x9E3779B9,
	D_SUM       = DELTA * ROUNDS;

	/**** Instance vars ****/
	// Subkeys
	private static int[] S = new int[4];

	// False for encipher, true for decipher
	private static boolean decrypt;

	// Initialization
	public static void engineInit(byte[] key, boolean decipher)throws InvalidKeyException 
	{
		if (key == null ) 
			throw new InvalidKeyException("Null key");


		if (key.length != KEY_SIZE) 
			throw new InvalidKeyException("Invalid key length (req. 16 bytes)");


		generateSubKeys(key);
		decrypt = decipher;
	}

	// Encrypt one block of data with XTEA algorithm
	public static byte[] engineCrypt(byte[] in, int inOffset) 
	{
		
		// Pack bytes into integers
		int v0 = 
				((in[inOffset++] & 0xFF) << 24) |
				((in[inOffset++] & 0xFF) << 16) |
				((in[inOffset++] & 0xFF) <<  8) |
				((in[inOffset++] & 0xFF)      );
		int v1 = 
				((in[inOffset++] & 0xFF) << 24) |
				((in[inOffset++] & 0xFF) << 16) |
				((in[inOffset++] & 0xFF) <<  8) |
				((in[inOffset++] & 0xFF)      );

		int n = ROUNDS, sum;

		// Encipher
		if(!decrypt) 
		{
			sum = 0;
			System.out.println("ENC");
			while (n-- > 0) {
				v0  += ((v1 << 4 ^ v1 >>> 5) + v1) ^ (sum + S[sum & 3]);
				sum += DELTA;
				v1  += ((v0 << 4 ^ v0 >>> 5) + v0) ^ (sum + S[sum >> 11 & 3]);
			}
			// Decipher
		}
		else 
		{
			sum = D_SUM;
			System.out.println("DEC");
			while (n-- > 0) {
				v1  -= ((v0 << 4 ^ v0 >>> 5) + v0) ^ (sum + S[sum >> 11 & 3]);
				sum -= DELTA;
				v0  -= ((v1 << 4 ^ v1 >>> 5) + v1) ^ (sum + S[sum & 3]);
			}            
		}        

		// Unpack and return
		int outOffset = 0;
		byte[] out = new byte[BLOCK_SIZE];
		out[outOffset++] = (byte)(v0 >>> 24);
		out[outOffset++] = (byte)(v0 >>> 16);
		out[outOffset++] = (byte)(v0 >>>  8);
		out[outOffset++] = (byte)(v0       );

		out[outOffset++] = (byte)(v1 >>> 24);
		out[outOffset++] = (byte)(v1 >>> 16);
		out[outOffset++] = (byte)(v1 >>>  8);
		out[outOffset++] = (byte)(v1       );

		return out;
	}

	// Subkey generator
	public static void generateSubKeys(byte[] key) 
	{
		for(int off=0, i=0; i<4; i++) 
			S[i] = ((key[off++]&0xFF) << 24) |((key[off++]&0xFF) << 16) |((key[off++]&0xFF) <<  8) |((key[off++]&0xFF)      );
	}

	public static void main(String args []) throws InvalidKeyException, UnsupportedEncodingException
	{
		engineInit("AMAZINGSPIDERMAN".getBytes("UTF-8") , false);
		byte [] crypt=engineCrypt("HELLO WORLD".getBytes(), 0);
		String testenc = new Base64().encodeToString(crypt); 
		System.out.println("CIPHER : "+testenc);

		engineInit("AMAZINGSPIDERMAN".getBytes("UTF-8") , true);
		byte [] decrypt=engineCrypt(testenc.getBytes(), 0);
		String testdec= new Base64().encodeToString(decrypt); 
		System.out.println("DeCIPHER : "+testdec);
	}
	
	private static String bytes2String(byte[] bytes) 
	{
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i<bytes.length; i++) 
		{
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}
}

