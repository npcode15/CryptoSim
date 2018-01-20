package Misc;

import java.security.InvalidKeyException;  
import java.security.Key;
import java.security.NoSuchAlgorithmException;  
import javax.crypto.*;  
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;  
 
public class BlowFish {  
      
    private static String cipher_key = "Blowfish";  
    private static Cipher cipher;  
    private static KeyGenerator k_generator;  
    private static Key secret_key;  
      
    public static void main(String args[]) throws NoSuchAlgorithmException, NoSuchPaddingException{  
        try {  
        	System.out.println("INDIA");
            execute_data_encryption(); 
        } catch (InvalidKeyException ex) {  
            System.out.println("Error! " + ex.getMessage());  
        }  
    }  
  
    private static byte[] decrypt_data(byte[] encrypted_text)   
            throws InvalidKeyException {  
        byte[] decrypted = null;  
        try {  
            //Initialize cipher with the generated secret key  
            cipher.init(Cipher.DECRYPT_MODE, secret_key);  
              
            //Do the decryption  
            decrypted = cipher.doFinal(encrypted_text);  
             
        } catch (IllegalBlockSizeException | BadPaddingException ex) {  
            System.out.println("Error! " + ex.getMessage());  
        }  
         return decrypted;  
    }  
  
  
    private static byte[] encrypt_data(String plain_text) throws InvalidKeyException 
    {  
        byte[] encrypted = null;  
        try {  
            //Re-initialize the cipher  for decription  
            cipher.init(Cipher.ENCRYPT_MODE, secret_key);  
              
            //Do the encryption  
            encrypted = cipher.doFinal(plain_text.getBytes());  
              
        } catch (IllegalBlockSizeException | BadPaddingException ex) {  
            System.out.println("Error! " + ex.getMessage());  
        }  
        return encrypted;  
    }  
  
    private static void execute_data_encryption() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException 
    {  
               //Create Cipher based on the previous encryption key code  
            //cipher = Cipher.getInstance(cipher_key);  
            secret_key = new SecretKeySpec("WORLD".getBytes(),"Blowfish");
            //Pass the data to be encrypted. in this case a simple string  
            String plain_text = JOptionPane.showInputDialog("Type in the text to be encrypted");  
                      
              
            //Encrypt the text and store the data as bytes array               
            byte[] encrypted_data = encrypt_data(plain_text);  
              
            //Decrypt the the text back to its original form as bytes  
            byte[] decrypted_data = decrypt_data(encrypted_data);  
              
            //Show dialog with the result summary   
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),   
                    "The encrypted data is: "  
                    + new String(encrypted_data) + "\n" + "Dectrypted data is : "   
                    + new String(decrypted_data),  
                    "Encryption Summary",  JOptionPane.PLAIN_MESSAGE);  
              
            System.exit(0);  
    }  
}