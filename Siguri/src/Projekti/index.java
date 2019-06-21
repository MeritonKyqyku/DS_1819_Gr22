package Projekti;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class index extends StackPane {
	index () {
	
		StackPane pane=new StackPane();
		VBox vBox=new VBox();
		HBox hBox=new HBox();
		
		Text head=new Text("Members");
		head.setTranslateX(20);
		head.setFont(Font.font("Sans-Seriff",FontWeight.BOLD,42));
		
		
		hBox.getChildren().addAll(head);
		hBox.setAlignment(Pos.CENTER);
		
		 
		
		Rectangle rect=new Rectangle(1100,900,Color.HONEYDEW);
//
//		ImageView addMemberImage=new ImageView();
//		addMemberImage.setFitWidth(100);
//		addMemberImage.setFitHeight(100);
		
		Button addMemberBtn=new Button("Shto Anetar");
		addMemberBtn.setMinHeight(35);
		addMemberBtn.setMinWidth(100);
//		ImageView memberSettingsImage=new ImageView();
//		memberSettingsImage.setFitWidth(100);
//		memberSettingsImage.setFitHeight(100);
		
		Button memberSettingsBtn=new Button("Log In");
		memberSettingsBtn.setMinWidth(100);
		memberSettingsBtn.setMinHeight(35);
		
		
		vBox.getChildren().addAll(hBox,addMemberBtn,memberSettingsBtn);
		vBox.setSpacing(30);
		vBox.setAlignment(Pos.CENTER);
		getChildren().addAll(rect,vBox);
		
//		primaryStage.setTitle("Rrethi i pozicionuar ne mes te dritares"); 
//		primaryStage.setScene(scene); 
//		primaryStage.show(); 
//		
		addMemberBtn.setOnAction(e->{
			try {
				Coordinator.setAdminPane();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		memberSettingsBtn.setOnAction(e->{
			try {
				Coordinator.setLoginPane();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

}
