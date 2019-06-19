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
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
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
	public static Connection getDBConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DBConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka?useTimezone=true&serverTimezone=UTC","root","");
		}
		catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DBConnection;
	}
	
	public static void setDbConnection(Connection dbConnection)
	{
		try {
			//Ketu e kam ba lidhjen me databaze, veq jepni vlera se qysh i ka MYSQL i juve edhe funksionon
			String dbUser="root";
			String dbPassword="";
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka?useTimezone=true&serverTimezone=UTC",dbUser,dbPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	public static void dekripto(String key,int cipherMode, String useri) throws InvalidKeyException,NoSuchAlgorithmException,InvalidKeySpecException,
	NoSuchPaddingException,IOException,InvalidAlgorithmParameterException{
		String ini=useri;
		InputStream teksti = new ByteArrayInputStream(ini.getBytes(Charset.forName("UTF-8")));
		//String uni=passi;
		//InputStream tekst = new ByteArrayInputStream(uni.getBytes(Charset.forName("UTF-8")));

			
			OutputStream Dalja = null;
			//Dalja.write(useri.getBytes());
			OutputStream Dalje = null;
			//Dalje.write(passi.getBytes());
			DESKeySpec desKeySpecs= new DESKeySpec(key.getBytes());
			SecretKeyFactory skf=SecretKeyFactory.getInstance("DES");
			SecretKey secretKey=skf.generateSecret(desKeySpecs);
			byte[] ivBytes=new byte[8];
			IvParameterSpec iv=new IvParameterSpec(ivBytes);
			System.out.println("MIRE O DERI QETU");
			
			
			
			Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
			if(cipherMode ==Cipher.DECRYPT_MODE) {
				cipher.init(Cipher.DECRYPT_MODE,secretKey,iv,SecureRandom.getInstance("SHA1PRNG"));
				System.out.println("MIRE O DERI QETU 2");
				CipherOutputStream cis=new CipherOutputStream(Dalja,cipher);
				//CipherOutputStream cys=new CipherOutputStream(Dalje,cipher);
				
				try {
				write(teksti,cis);
			//	write(teksti,cys);
				}catch(Exception e) {
					System.out.println("MIRE O DERI QETU  4fila");
				}
			}
				
			}
			
			
			
	

public static void write(InputStream cis,OutputStream out) throws IOException, InterruptedException {
	byte[] buffer= new byte[64];
	int numOfBytesRead=0;
	while((numOfBytesRead-cis.read(buffer))!=-1) {
		out.write(buffer,0,numOfBytesRead);
	}
		
	
	}
	
	public static void main(String args[]) throws SocketException,IOException, ClassNotFoundException, InvalidAlgorithmParameterException 
	{
		
		  
		  DatagramSocket socket = new DatagramSocket(12340);
		  
        while (true) {
            try {
            	System.out.println("Serveri po pret komunikim");
                byte[] buf = new byte[256];
                
              

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
               
                
                socket.receive(packet);
                
                String userit=new String(packet.getData());
                System.out.println(userit);
                System.out.println(userit);
                String useri=userit;
              //  String passi=userit.substring(8,16);
                
                System.out.println("Username: "+useri);
               // System.out.println("Password: "+passi);

//                System.out.println("### socket.getLocalPort():" + socket.getLocalPort() + " | socket.getPort(): " + socket.getPort());

                // figure out response
                String dString = "Server is responding:  asd  asdd";
                buf = new byte[256];
                buf = dString.getBytes();
                
                
                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.println("Useri from client: " + useri);
               // System.out.println("Passi eshte "+passi);
                try {
        			dekripto("12345678",Cipher.DECRYPT_MODE,useri);
        			System.out.println("u dekriptuaa");
        			System.out.println(useri);
        		}
        		catch(InvalidKeyException | NoSuchAlgorithmException|InvalidKeySpecException|NoSuchPaddingException|IOException e) {
        			System.out.println("GABIMI TE ENKRIPTO");
        		}
            }catch(Exception e) {
            	e.printStackTrace();
            }
        }
	}
}
         
                
//                
//                packet = new DatagramPacket(dString.getBytes(), dString.getBytes().length, address, port);
//                System.out.println("### Sending for packet.hashCode(): " + packet.hashCode() + " | packet.getPort(): " + packet.getPort());
//
//                fila
//
//                System.out.println("Now sending the response back to UDP client.");
//
//                DatagramSocket sendingDatagramSocket = new DatagramSocket();
////                sendingDatagramSocket.send(packet);
//                sendingDatagramSocket.close();
  //              System.out.println("I am done");
           
			//Ketu eshte nje pyetesor qe shikon se a egziston "Username" i kerkuar
//				try {	
//					    
//						String testQuery="Select EXISTS(SELECT * FROM bibliotekistet WHERE username=?)";
//						PreparedStatement preparedStatement=getDBConnection().prepareStatement(testQuery);
//						preparedStatement.setString(1, useri);
//						ResultSet resultSet1=preparedStatement.executeQuery();
//						System.out.println("1111");
//						
//							if(resultSet1.next())
//							{
//								//Nese egziston username, Mysql kthen pergjigjjen "1", nese jo kthen "0"
//								if(resultSet1.getString(1).matches("1"))
//								{
//									String statusiQuery="Select statusi from bibliotekistet where username=?";
//									preparedStatement=DBConnection.prepareStatement(statusiQuery);
//									preparedStatement.setString(1, useri);
//									resultSet1=preparedStatement.executeQuery();
//									if(resultSet1.next())
//									{
//										
//										//A eshte aktiv username i dhene
//										if(resultSet1.getString(1).matches("1"))
//										{
//											//Kerkohet fjalekalimi dhe "salt" nga databaza
//											String checkPassQuery="SELECT pass,salt from bibliotekistet where username=?";
//											PreparedStatement checkPasswordStatement=DBConnection.prepareStatement(checkPassQuery);
//											checkPasswordStatement.setString(1, useri);
//											ResultSet resultSet2=checkPasswordStatement.executeQuery();
//											
//											//Verifikohet fjalekalimi
//											if(resultSet2.next())
//											{
//												//Pasi merren fjalekalimi dhe "salt" shikohet 
//												//se a pershtatet fjalekalimi i dhene me ate ne databaze
//												String pass=resultSet2.getString(1);
//												String salt=resultSet2.getString(2);
//	//											String hashedPassword=generateHash(passi,salt);
////												System.out.println(passi);
////												System.out.println(passi.toString().matches("admin"));
////												System.out.println(generateHash("admin", salt));
////												System.out.println(hashedPassword);
////												System.out.println(pass);
//												//thirret metoda "hashedPassword" dhe nese eshte me sukses, atehere vazhdojme
//												if(pass.matches(hashedPassword))
//												{
//													if(useri.matches("admin"))
//														{
//
//															System.out.println("admini sakt");
//															System.out.println("Admin got in");
//															String sendData=new String("Hello Admin!");
//															packet = new DatagramPacket(sendData.getBytes(), sendData.getBytes().length, address, port);
//															DatagramSocket sendingDatagramSocket = new DatagramSocket();
//															sendingDatagramSocket.send(packet);
//															sendingDatagramSocket.close();
//														}
//													else {
//														System.out.println("Filan got in");
//														System.out.println("je te filani");
//														String sendData=new String("Hello Filan!");
//														packet = new DatagramPacket(sendData.getBytes(), sendData.getBytes().length, address, port);
//														DatagramSocket sendingDatagramSocket = new DatagramSocket();
//														sendingDatagramSocket.send(packet);
//														sendingDatagramSocket.close();
//													}
//												}
//												//Nese nuk eshte i sakte fjalekalimi
//												else
//												{
//													String bad=new String ("Bad password");
//													System.out.println("passi soosht mire");
//													packet = new DatagramPacket(bad.getBytes(), bad.getBytes().length, address, port);
//													DatagramSocket sendingDatagramSocket = new DatagramSocket();
//													sendingDatagramSocket.send(packet);
//													sendingDatagramSocket.close();
////													socket.close();
//												}
//											}
//										}
//										//Nese eshte gjetur Username por nuk eshte aktiv,
//										else {
//											System.out.println("Username is not active!");
//											
//										}
//									}
//								}
//								//Nese kthehet vlera "0" nga Mysql, dmth nuk eshte gjetur username ne databaze
//								else
//								{
//									System.out.println("Username doesn't exist!");
//									
//								}
//							}
//					}catch (Exception e) {
//							e.printStackTrace();
//						}
//	            }catch(Exception e) {
//	            	System.out.println("sban");
//	            	socket.close();
//	            	}
//        	}
        	

        
	
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
	


	
