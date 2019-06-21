package Projekti;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;


public class primaryClass extends Application {
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	public static Stage primaryStage;
	@Override
	public void start(Stage stage) throws IOException
	{
		primaryClass.primaryStage=stage;
		Coordinator.setLoginPane();
		stage.setResizable(false);
		stage.show();
	}
}
