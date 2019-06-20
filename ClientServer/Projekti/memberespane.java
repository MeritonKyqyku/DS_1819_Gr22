package Projekti;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.*;

public class memberespane extends Application {
	public void start(Stage primaryStage) throws IOException {
		
	
	Text head=new Text("sign up");
	StackPane pane=new StackPane();
	Rectangle rect=new Rectangle(1100,1100);
	VBox vBox=new VBox();
	HBox Username=new HBox(5);
    HBox Password=new HBox(5);
    HBox pozitaa=new HBox(5);
    HBox pagaa=new HBox(5);
    HBox Stazhi =new HBox(5);
	//resultLabel eshte vetem per testim, do te fshihet ne verzionin perfundimtar
	Label resultLabel=new Label();
	
	
	//Private, ato qe na duhen vetem brenda kesaj klase
//	 Connection dbConnection;
	 TextField usernameIn=new TextField();
	 PasswordField passwordIn=new PasswordField();
	 TextField pozita=new TextField();
	 TextField paga=new TextField();
	 TextField stazhi=new TextField();
	 Label poz = new Label("Pozita");
	 Label pag = new Label("Paga");
	 Label stzh = new Label("Stazhi");
	 
	 
	 Button LoginBtn=new Button("Login");
	
		head.setFont(Font.font("Sans-seriff", FontWeight.BOLD, 40));
		vBox.getChildren().add(head);
		vBox.setSpacing(25);
		vBox.setAlignment(Pos.CENTER);
		
		// Krijimi i pjeses se futjes se username-it
		Label user=new Label("Username:");
		Username.setAlignment(Pos.CENTER);
		Username.getChildren().addAll(user,usernameIn);
		Stazhi.setAlignment(Pos.CENTER);
		Stazhi.setSpacing(28);
		Stazhi.getChildren().addAll(stzh,stazhi);
		
		//Krijimi i pjeses se futjes se password-it
		
		Label pass=new Label("Password: ");
		
		Password.setAlignment(Pos.CENTER);
		Password.getChildren().addAll(pass,passwordIn);
		pozitaa.setAlignment(Pos.CENTER);
		pozitaa.setSpacing(26);
		pozitaa.getChildren().addAll(poz,pozita);
		pagaa.setAlignment(Pos.CENTER);
		pagaa.setSpacing(28);
		pagaa.getChildren().addAll(pag,paga);
		
		
		//Vendosja e femijeve ne vBox
		vBox.getChildren().addAll(Username,Password,pozitaa,pagaa,Stazhi,LoginBtn,resultLabel);
		
		//Drejtekendesh per dizajn ku vendoset pas formes
		
		rect.setFill(Color.HONEYDEW);
		pane.getChildren().addAll(rect,vBox);
	    Scene scene = new Scene(pane, 900, 700);
		primaryStage.setTitle("Rrethi i pozicionuar ne mes te dritares"); 
		primaryStage.setScene(scene); 
		primaryStage.show(); 
		LoginBtn.setOnAction(e->{
			try {
				regjistro(usernameIn,passwordIn,pozita,paga,stazhi);
			} catch (IOException | InvalidKeySpecException e1) {
				e1.printStackTrace();
			}
		});
	
	
}

	private void regjistro(TextField usernameIn, PasswordField passwordIn, TextField pozita, TextField paga,
			TextField stazhi) throws IOException, InvalidKeySpecException {
		
		String ini=(usernameIn.getText()+"@"+passwordIn.getText()+"@"+pozita.getText()+"@"+paga.getText()+"@"+stazhi.getText());
		

		
		String key="12345678";
		
		
		try{

			 DESKeySpec desKeySpec=new DESKeySpec(key.getBytes());
			 SecretKeyFactory skf=SecretKeyFactory.getInstance("DES");
			 SecretKey myDesKey = skf.generateSecret(desKeySpec);
		    
		    Cipher desCipher;

		    // Create the cipher 
		    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		    
		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

		    //sensitive information
		    byte[] text = ini.getBytes();
		    System.out.println("Text : " + new String(text));
		   
		    // Encrypt the text
		    byte[] textEncrypted = desCipher.doFinal(text);

		    System.out.println("Text Encryted : " + textEncrypted);
		    
		    DatagramSocket socket = new DatagramSocket();
		

		    // send request
		    byte[] buf =textEncrypted;
		    
		    InetAddress address = InetAddress.getByName("localhost");
		    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 12340);
		    socket.send(packet);
		    

		    // get response
		    
		    Alert a = new Alert(AlertType.NONE);
		    byte[] receiveData=new byte [1024] ;
			
		    DatagramPacket receivePacket=new DatagramPacket(receiveData, receiveData.length);
		    socket.receive(receivePacket);
		    String s=new String(receivePacket.getData());
		    System.out.println("Got the response back from server."+"\n"+s);
		   
		    a.setAlertType(AlertType.CONFIRMATION); 
            a.setContentText("Keni regjistruar me sukses");
            if(s.matches("OK")) {
    			a.setContentText("Registered Sucssesfully");
    			
    			
    		}
            // show the dialog 
            a.show();

		    socket.close();
		    // Initialize the same cipher for decryption
		    
		  
		    
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
	   
		
	}

	public static void main(String[] args)
	
	{
	
		Application.launch(args);
	
	}
}
