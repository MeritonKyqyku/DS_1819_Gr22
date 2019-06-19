package Projekti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.net.ssl.X509KeyManager;

public class DES
{    
	public static void main(String[] argv) throws IOException {
		
		try{

			InputStream is = new FileInputStream("C:\\Users\\Admin\\Desktop\\asd"); 
			BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
			String line = buf.readLine(); StringBuilder sb = new StringBuilder(); 
			while(line != null){ 
				sb.append(line).append("\n"); line = buf.readLine(); 
			} 
			String celesiString = sb.toString(); 
			
			
			
			
//		    DESKeySpec desKeySpec=new DESKeySpec(celesiString.getBytes());
//		    SecretKeyFactory skf=SecretKeyFactory.getInstance("DES");
//		    SecretKey myDesKey = skf.generateSecret(desKeySpec);
		    
		    KeyGenerator kg = KeyGenerator.getInstance("DES");
		    Cipher desCipher;

		    // Create the cipher 
		    desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		    Key key= kg.generateKey();
		    
		    // Initialize the cipher for encryption
//		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

		    desCipher.init(Cipher.ENCRYPT_MODE, key);
		    //sensitive information
		    byte[] text = "No body sees me".getBytes();
		    String x=new String (text);
		    System.out.println(x);

		    System.out.println("Text [Byte Format] : " + text);
		    System.out.println("Text : " + new String(text));
		   
		    // Encrypt the text
		    byte[] textEncrypted = desCipher.doFinal(text);
		    String textEncryptedString=new String (textEncrypted);
		    byte iv[] = desCipher.getIV();

		    System.out.println("Text Encryted : " + textEncryptedString);
		    
		    IvParameterSpec dps = new IvParameterSpec(iv);
		    desCipher.init(Cipher.DECRYPT_MODE, key, dps);
		    byte output[] = c.doFinal(encrypted);
		    System.out.println(new String(output));
		    // Initialize the same cipher for decryption
//		    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);

		    // Decrypt the text
		    byte[] textDecrypted = desCipher.doFinal(textEncrypted);
		    
		    System.out.println("Text Decryted : " + new String(textDecrypted));
		    
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   
	}
}