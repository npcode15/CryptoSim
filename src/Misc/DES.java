package Misc;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
  
/** 
 *  
 * @author Anuj 
 *  
 */  
public class DES {  
  
 private static Cipher encryptCipher;  
 private static Cipher decryptCipher;  
  
 public static void main(String[] args) throws UnsupportedEncodingException {  
  try {  
   /*KeyGenerator keygenerator = KeyGenerator.getInstance("DES");  
   SecretKey secretKey = keygenerator.generateKey();  [B@1c958af*/
   
   SecretKey secretKey = new SecretKeySpec("encrypyt".getBytes("UTF-8"), "DES");
   
   encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
   encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);  
   byte[] encryptedData = encryptData("Classified Information!      ");  
  
   String encryptedValue = new Base64().encodeToString(encryptedData);
   
   decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
   decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);  
   byte[] decryptedData=decryptData(new Base64().decode(encryptedValue));  
   
   String decryptedValue = new Base64().encodeToString(decryptedData);
  
   System.out.println("encryptedValue -> "+encryptedValue);
   System.out.println("decryptedValue -> "+new Base64().decode(encryptedValue));
   
  } catch (NoSuchAlgorithmException e) {  
   e.printStackTrace();  
  } catch (NoSuchPaddingException e) {  
   e.printStackTrace();  
  } catch (InvalidKeyException e) {  
   e.printStackTrace();  
  } catch (IllegalBlockSizeException e) {  
   e.printStackTrace();  
  } catch (BadPaddingException e) {  
   e.printStackTrace();  
  }  
  
 }  
  
 /** 
  * Encrypt Data 
  * @param data 
  * @return 
  * @throws IllegalBlockSizeException 
  * @throws BadPaddingException 
  */  
 private static byte[] encryptData(String data)  
   throws IllegalBlockSizeException, BadPaddingException {  
  System.out.println("Data Before Encryption :" + data);  
  byte[] dataToEncrypt = data.getBytes();  
  byte[] encryptedData = encryptCipher.doFinal(dataToEncrypt);  
  System.out.println("Encryted Data: " + encryptedData);  
  
  return encryptedData;  
 }  
  
 /** 
  * Decrypt Data 
  * @param data 
 * @return 
  * @throws IllegalBlockSizeException 
  * @throws BadPaddingException 
  */  
 private static byte[] decryptData(byte[] data)  
   throws IllegalBlockSizeException, BadPaddingException {  
  byte[] textDecrypted = decryptCipher.doFinal(data);  
  System.out.println("Decryted Data: " + new String(textDecrypted));
return textDecrypted;  
 }  
}  