package Projekti;


import java.net.*;
import java.io.*;
import java.util.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;



import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Serveri {
	private static Connection DBConnection;
	
	
	
	
	public static String generateHash(String pass,String salt)
	{
		String saltedPass=pass.concat(salt.toString());
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(saltedPass.getBytes());
			byte[] digest=md.digest();
			String result=DatatypeConverter.printHexBinary(digest).toLowerCase();
			return result;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
 
	
	public static void main(String args[]) throws SocketException,IOException, ClassNotFoundException, InvalidAlgorithmParameterException 
	{
		Coordinator.setDbConnection(DBConnection);
		String key="12345678";
	
		DatagramSocket socket = new DatagramSocket(12340);
	  
    while (true) {
        try {
        	System.out.println("Serveri po pret komunikim");
            byte[] buf = new byte[256];
            
          

            // receive request
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
           
            
            socket.receive(packet);
            
            String userit=new String(packet.getData()).replaceAll("\u0000.*", "");
            
            String dString = "Server is responding:  asd  asdd";
            buf = new byte[256];
            buf = dString.getBytes();
            String textEncrypted=new String(packet.getData());
            
            // send the response to the client at "address" and "port"
           
            
		try{

				DESKeySpec desKeySpec=new DESKeySpec(key.getBytes());
				SecretKeyFactory skf=SecretKeyFactory.getInstance("DES");
				SecretKey myDesKey = skf.generateSecret(desKeySpec);
			    Cipher desCipher;
	
			    // Create the cipher 
			    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			    
			    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
	
			    // Decrypt the text
			    byte[] textDecrypted = desCipher.doFinal(userit.getBytes());
			   
			    
			    System.out.println("Text Decryted : " + new String(textDecrypted));
			    InetAddress address = packet.getAddress();
			    int port = packet.getPort();
			    String useriT= new String(textDecrypted);
			    String[] arrOfStr =useriT.split("@", 5);
			    int count = useriT.length() - useriT.replace("@", "").length();
			    System.out.println(count);
	          
			    for (int i=0;i<arrOfStr.length;i++)
			    {
			    	System.out.println(arrOfStr[i]);
			    }
			    if(count==1) 
			    {
	          
		    			String useri=arrOfStr[0];
		    			String passi=arrOfStr[1];
				          
			          try {	
						    
							String testQuery="Select EXISTS(SELECT * FROM puntori WHERE username=?)";
							PreparedStatement preparedStatement=Coordinator.getDBConnection().prepareStatement(testQuery);
							preparedStatement.setString(1, useri);
							ResultSet resultSet1=preparedStatement.executeQuery();
							System.out.println("1111");
							
								if(resultSet1.next())
								{
									//Nese egziston username, Mysql kthen pergjigjjen "1", nese jo kthen "0"
									if(resultSet1.getString(1).matches("1"))
									{
										
												//Kerkohet fjalekalimi dhe "salt" nga databaza
												String checkPassQuery="SELECT pass,salt from puntori where username=?";
												PreparedStatement checkPasswordStatement=Coordinator.getDBConnection().prepareStatement(checkPassQuery);
												checkPasswordStatement.setString(1, useri);
												ResultSet resultSet2=checkPasswordStatement.executeQuery();
												
												//Verifikohet fjalekalimi
												if(resultSet2.next())
												{
													//Pasi merren fjalekalimi dhe "salt" shikohet 
													//se a pershtatet fjalekalimi i dhene me ate ne databaze
													String pass=resultSet2.getString(1);
													String salt=resultSet2.getString(2);
													String hashedPassword=generateHash(passi,salt);
													System.out.println(passi);
													System.out.println(passi.toString().matches("admin"));
													System.out.println(generateHash("admin", salt));
													System.out.println(hashedPassword);
													System.out.println(pass);
													//thirret metoda "hashedPassword" dhe nese eshte me sukses, atehere vazhdojme
													if(pass.matches(hashedPassword))
													{
														if(useri.matches("admin"))
															{
																System.out.println("Admin got in");
																Statement stmt =Coordinator.getDBConnection().createStatement();
																ResultSet rez=stmt.executeQuery("select username,pozita,rroga,stazhi from puntori where username='"+useri+"';");
																
																while(rez.next()) {
																	String usernamei=rez.getString(1);
																	String pozitaa=rez.getString(2);
																	String rroga=rez.getString(3);
																	String stazhi=rez.getString(4);
																	String dergesa=(usernamei+"@"+pozitaa+"@"+rroga+"@"+stazhi);
																	
																	packet = new DatagramPacket(dergesa.getBytes(), dergesa.getBytes().length, address, port);
																	DatagramSocket sendingDatagramSocket = new DatagramSocket();
																	sendingDatagramSocket.send(packet);
																	sendingDatagramSocket.close();
																	Coordinator.getDBConnection().close();
																
																}
																
																
																
																
															}
														else {
																Statement stmt =Coordinator.getDBConnection().createStatement();
																ResultSet rez=stmt.executeQuery("select username,pozita,rroga,stazhi from puntori where username='"+useri+"';");
																
																while(rez.next()) {
																	String usernamei=rez.getString(1);
																	String pozitaa=rez.getString(2);
																	String rroga=rez.getString(3);
																	String stazhi=rez.getString(4);
																	String dergesa=(usernamei+"@"+pozitaa+"@"+rroga+"@"+stazhi);
																	packet = new DatagramPacket(dergesa.getBytes(), dergesa.getBytes().length, address, port);
																	
																	DatagramSocket sendingDatagramSocket = new DatagramSocket();
																	sendingDatagramSocket.send(packet);
																	sendingDatagramSocket.close();
																	Coordinator.getDBConnection().close();
															}
															
															
//															System.out.println("Filan got in");
//															System.out.println("je te filani");
//															String sendData=new String("Hello Filan!");
//															packet = new DatagramPacket(sendData.getBytes(), sendData.getBytes().length, address, port);
//															DatagramSocket sendingDatagramSocket = new DatagramSocket();
//															sendingDatagramSocket.send(packet);
//															sendingDatagramSocket.close();
														}
													}
													//Nese nuk eshte i sakte fjalekalimi
													else
													{
														String bad=new String ("Bad password");
														System.out.println("passi soosht mire");
														
			//											socket.close();
													}
												}
											}
											
								}
						}catch (Exception e) {
								e.printStackTrace();
								}
	       }
	          else {
	        	  
					  String username =arrOfStr[0];
					  String pass=arrOfStr[1];
					  String pozita=arrOfStr[2];
					  String rroga=arrOfStr[3];
					  String stazhi=arrOfStr[4];
					  Random rand=new Random();
					  rand.nextInt(2147483647);
					  String salt=new Integer(rand.nextInt(2147483647)).toString();
					  
					  String passi=generateHash(pass,salt);
					  
					  
					  String dbUser="root";
					  String dbPassword="";
					  Class.forName("com.mysql.jdbc.Driver");
					  Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/pun?useTimezone=true&serverTimezone=UTC",dbUser,dbPassword);
					  Statement stmt = conn.createStatement();
					  stmt.executeUpdate("insert into puntori(username,pass,salt,pozita,rroga,stazhi) VALUES ('"+username+"','"+passi+"','"+salt+"','"+pozita+"','"+rroga+"','"+stazhi+"');");
					  conn.close();
					  String sendData="OK";
					  packet = new DatagramPacket(sendData.getBytes(), sendData.getBytes().length, address, port);
					  DatagramSocket sendingDatagramSocket = new DatagramSocket();
					  sendingDatagramSocket.send(packet);
					  sendingDatagramSocket.close();
					  
					  }

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
		} 
  }catch(Exception e) {
  	e.printStackTrace();
  }
    }
		
    

				
            
        
	}
}
        	


        

	//Metoda qe krijon "hash Code" te fjalekalimit te dhene me "salt te databazes
	
	
	
	
//	public class EnterEvent implements EventHandler<KeyEvent>{
//		@Override
//		public void handle(KeyEvent e) {
//			if(e.getCode()==KeyCode.ENTER)
//				LoginBtn.fire();
//		}
//	}
        
    
//	 String generateHash(String saltAndPass) throws NoSuchAlgorithmException
//	{
//		MessageDigest md=MessageDigest.getInstance("MD5");
//		md.update(saltAndPass.getBytes());
//		byte[] digest=md.digest();
//		String result=DatatypeConverter.printHexBinary(digest).toLowerCase();
//		return result;
//	}
	


	
