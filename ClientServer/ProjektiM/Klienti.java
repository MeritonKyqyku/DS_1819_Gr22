package Projekti;

import java.net.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Date;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javafx.application.Application;
import javafx.stage.Stage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.DatatypeConverter;

//import LoginPane.EnterEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Klienti extends Application {

	
	public static String generateHash(String pass,String salt)
	{
		String saltedPass=pass+(salt.toString());
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
	public void start(Stage primaryStage) throws IOException {
		StackPane pane=new StackPane();
		Rectangle rect=new Rectangle(1100,1100);
		VBox vBox=new VBox();
		HBox Username=new HBox(5);
	    HBox Password=new HBox(5);
		//resultLabel eshte vetem per testim, do te fshihet ne verzionin perfundimtar
		Label resultLabel=new Label();
		
		
		//Private, ato qe na duhen vetem brenda kesaj klase
//		 Connection dbConnection;
		 TextField usernameIn=new TextField();
		 PasswordField passwordIn=new PasswordField();
		 Button LoginBtn=new Button("Login");
		 Label head=new Label("Login");
			head.setFont(Font.font("Sans-seriff", FontWeight.BOLD, 40));
			vBox.getChildren().add(head);
			vBox.setSpacing(25);
			vBox.setAlignment(Pos.CENTER);
			
			// Krijimi i pjeses se futjes se username-it
			Label user=new Label("Username:");
			Username.setAlignment(Pos.CENTER);
			Username.getChildren().addAll(user,usernameIn);
			
			//Krijimi i pjeses se futjes se password-it
			
			Label pass=new Label("Password: ");
			Password.setAlignment(Pos.CENTER);
			Password.getChildren().addAll(pass,passwordIn);
			
			//Vendosja e femijeve ne vBox
			vBox.getChildren().addAll(Username,Password,LoginBtn,resultLabel);
			
			//Drejtekendesh per dizajn ku vendoset pas formes
			
			rect.setFill(Color.HONEYDEW);
			pane.getChildren().addAll(rect,vBox);
		    Scene scene = new Scene(pane, 900, 700);
			primaryStage.setTitle("Rrethi i pozicionuar ne mes te dritares"); 
			primaryStage.setScene(scene); 
			primaryStage.show(); 
			LoginBtn.setOnAction(e->{
			try {
				Soketti(usernameIn,passwordIn);
			} catch (IOException | InvalidAlgorithmParameterException e1) {
				e1.printStackTrace();
			}
		});
	}
	public void Soketti(TextField usernameIn, PasswordField passwordIn)  throws IOException, InvalidAlgorithmParameterException {
		String User = null;
		
		
		
		try {
			enkripto("12345678",Cipher.ENCRYPT_MODE,usernameIn,passwordIn,User);
			System.out.println("u enkriptuaa");
		}
		catch(InvalidKeyException | NoSuchAlgorithmException|InvalidKeySpecException|NoSuchPaddingException|IOException e) {
			System.out.println("GABIMI TE ENKRIPTO");
		}
		
		
		
		
//		DBConnection=getDBConnection();
		
		
		// get a datagram socket
        DatagramSocket socket = new DatagramSocket();
        System.out.println("### socket.getLocalPort():" + socket.getLocalPort() + " | socket.getPort(): " + socket.getPort());

        // send request
//        byte[] buf =usernameIn.getText().getBytes();
//        byte[] buf1=passwordIn.getText().getBytes();
//        InetAddress address = InetAddress.getByName("localhost");
//        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 12340);
//        socket.send(packet);
//        DatagramPacket packet1 =new DatagramPacket(buf1,buf1.length,address,12340);
//        socket.send(packet1);

        // get response
//        packet = new DatagramPacket(buf, buf.length);
        
//        byte[] receiveData=new byte [1024] ;
// 
//        DatagramPacket receivePacket=new DatagramPacket(receiveData, receiveData.length);
//        socket.receive(receivePacket);
//        String s=new String(receivePacket.getData());
//        System.out.println("Got the response back from server."+"\n"+s);
//        socket.receive(packet1);
//        System.out.println(packet1);

        // display response
//        String received = new String(packet.getData());
//        String recived1=new String(packet1.getData());
//        System.out.println("Quote of the Moment: " + received+recived1);

//        socket.close();
        
		
	}
	public static void enkripto(String key,int cipherMode,TextField in,TextField pas,String out) throws InvalidKeyException,NoSuchAlgorithmException,InvalidKeySpecException,
	NoSuchPaddingException,IOException,InvalidAlgorithmParameterException{
			String ini=in.getText();
			InputStream teksti = new ByteArrayInputStream(ini.getBytes(Charset.forName("UTF-8")));
			String uni=pas.getText();
			InputStream tekst = new ByteArrayInputStream(uni.getBytes(Charset.forName("UTF-8")));

			System.out.println(teksti);
			OutputStream Dalja = null;
			OutputStream Dalje=null;
			DESKeySpec desKeySpecs= new DESKeySpec(key.getBytes());
			SecretKeyFactory skf=SecretKeyFactory.getInstance("DES");
			SecretKey secretKey=skf.generateSecret(desKeySpecs);
			byte[] ivBytes=new byte[8];
			IvParameterSpec iv=new IvParameterSpec(ivBytes);
			System.out.println("MIRE O DERI QETU");
			
			
			
			Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
			if(cipherMode ==Cipher.ENCRYPT_MODE) {
				cipher.init(Cipher.ENCRYPT_MODE,secretKey,iv,SecureRandom.getInstance("SHA1PRNG"));
				System.out.println("MIRE O DERI QETU 2");
				CipherInputStream cis=new CipherInputStream(teksti,cipher);
				CipherInputStream cys=new CipherInputStream(tekst,cipher);
				
				try {
				write(cis,Dalja,cys,Dalje);
				}catch(Exception e) {
					System.out.println("MIRE O DERI QETU  4fila");
				}
				
			}
			
			
	}
	public static void write(InputStream cis,OutputStream out, CipherInputStream cys, OutputStream dalje) throws IOException, InterruptedException {
		byte[] buffer= new byte[64];
		int numOfBytesRead = 0;
		while((numOfBytesRead-cis.read(buffer))!=-1) {
			out.write(buffer,0,numOfBytesRead);
		}
		
		
//		String ini=in.toString();
//		String con=ini.substring(31);
//		String ini1=out.toString();
//		String con1=ini1.substring(31);
		out.close();
		cis.close();
		DatagramSocket socket = new DatagramSocket();
        System.out.println("### socket.getLocalPort():" + socket.getLocalPort() + " | socket.getPort(): " + socket.getPort());
        
        

        // send request
        byte[] buf =new byte [cis.available()];
        System.out.println("ka sukses qetu");
        
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 12340);
        socket.send(packet);
       

        // get response
//        packet = new DatagramPacket(buf, buf.length);
//        
//        byte[] receiveData=new byte [1024] ;
// 
//        DatagramPacket receivePacket=new DatagramPacket(receiveData, receiveData.length);
//        socket.receive(receivePacket);
//        String s=new String(receivePacket.getData());
//        System.out.println("Got the response back from server."+"\n"+s);
		
	}
	
	public static void main(String[] args) throws IOException

	{

		Application.launch(args);

	}
	
	
	
	
}
