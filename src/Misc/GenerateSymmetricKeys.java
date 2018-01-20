package Misc;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class GenerateSymmetricKeys {

    private static String generateKey(String keyAlgorithm) {
    	 String newkey="";
        try { 

            KeyGenerator keyGen = KeyGenerator.getInstance(keyAlgorithm);
            SecretKey key = keyGen.generateKey();

            System.out.println(key.getFormat());
            byte[] keyBytes = key.getEncoded();
            int numBytes = keyBytes.length;
            
			for(int i=0;i<numBytes;i++)
               newkey+=keyBytes[i];
        } catch (NoSuchAlgorithmException e) {

            System.out.println("Exception");
            System.out.println("No such algorithm: " + keyAlgorithm);
        }
		return newkey;

    	/*try
    	{
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			SecretKey key = keyGen.generateKey();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
*/
    }


    public static void main(String[] args) {

        // Generate a DES key
        System.out.println(generateKey("AES"));
        
/*
        // Generate a Blowfish key
        generateKey("Blowfish");

        // Generate a DESede key
        generateKey("DESede");*/
    	//generateKey();    
        }
}

