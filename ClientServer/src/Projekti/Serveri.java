package Projekti;


import java.net.*;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Serveri extends Application {
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
	
	
 
	
	@Override
	public void start(Stage primaryStage) throws SocketException,IOException, ClassNotFoundException {
		  
		  DatagramSocket socket = new DatagramSocket(8002);

        while (true) {
            try {
                byte[] buf = new byte[256];
                byte[] buf1=new byte[256];
              

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                DatagramPacket packet1 =new DatagramPacket(buf1,buf1.length);
                socket.receive(packet1);
                String useri=new String(packet.getData());
                String passi=new String(packet1.getData());

                System.out.println("### socket.getLocalPort():" + socket.getLocalPort() + " | socket.getPort(): " + socket.getPort());

                // figure out response
                String dString = "Server is responding:  asd  asdd";
                buf = new byte[256];
                buf = dString.getBytes();
                buf1 =new byte[256];
                
                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.println("useri from client: " + useri);
                System.out.println("Passi eshte "+passi);
                
                packet = new DatagramPacket(dString.getBytes(), dString.getBytes().length, address, port);
//                System.out.println("### Sending for packet.hashCode(): " + packet.hashCode() + " | packet.getPort(): " + packet.getPort());
//
//                //Thread.sleep(5000);
//
//                System.out.println("Now sending the response back to UDP client.");
//
//                DatagramSocket sendingDatagramSocket = new DatagramSocket();
//                sendingDatagramSocket.send(packet);
//                sendingDatagramSocket.close();
//                System.out.println("I am done");
           
			//Ketu eshte nje pyetesor qe shikon se a egziston "Username" i kerkuar
try {	
	    
		String testQuery="Select EXISTS(SELECT * FROM bibliotekistet WHERE username=?)";
		PreparedStatement preparedStatement=getDBConnection().prepareStatement(testQuery);
		preparedStatement.setString(1, useri);
		ResultSet resultSet1=preparedStatement.executeQuery();
		System.out.println("1111");
		
			if(resultSet1.next())
			{
				//Nese egziston username, Mysql kthen pergjigjjen "1", nese jo kthen "0"
				if(resultSet1.getString(1).matches("1"))
				{
					String statusiQuery="Select statusi from bibliotekistet where username=?";
					preparedStatement=DBConnection.prepareStatement(statusiQuery);
					preparedStatement.setString(1, useri);
					resultSet1=preparedStatement.executeQuery();
					if(resultSet1.next())
					{
						
						//A eshte aktiv username i dhene
						if(resultSet1.getString(1).matches("1"))
						{
							//Kerkohet fjalekalimi dhe "salt" nga databaza
							String checkPassQuery="SELECT pass,salt from bibliotekistet where username=?";
							PreparedStatement checkPasswordStatement=DBConnection.prepareStatement(checkPassQuery);
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
								//thirret metoda "hashedPassword" dhe nese eshte me sukses, atehere vazhdojme
								if(pass.matches(hashedPassword))
								{
									if(useri=="admin")
										{
											System.out.println("admini sakt");
										}
									else {
										System.out.println("je te filani");
									}
								}
								//Nese nuk eshte i sakte fjalekalimi
								else
								{
									System.out.println("passi soosht mire");
								}
							}
						}
						//Nese eshte gjetur Username por nuk eshte aktiv,
						else {
							System.out.println("Username is not active!");
							
						}
					}
				}
				//Nese kthehet vlera "0" nga Mysql, dmth nuk eshte gjetur username ne databaze
				else
				{
					System.out.println("Username doesn't exist!");
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
            }catch(Exception e) {
            	System.out.println("sban");}
            }
	
	//Metoda qe krijon "hash Code" te fjalekalimit te dhene me "salt te databazes
//	private String generateHash(String saltAndPass) throws NoSuchAlgorithmException
//	{
//		MessageDigest md=MessageDigest.getInstance("MD5");
//		md.update(saltAndPass.getBytes());
//		byte[] digest=md.digest();
//		String result=DatatypeConverter.printHexBinary(digest).toLowerCase();
//		return result;
//	}
	
	
	
//	public class EnterEvent implements EventHandler<KeyEvent>{
//		@Override
//		public void handle(KeyEvent e) {
//			if(e.getCode()==KeyCode.ENTER)
//				LoginBtn.fire();
//		}
//	}
        
    }

	

	
	public static void main(String[] args) throws SocketException, InterruptedException

	{

		Application.launch(args);

	}
	
}