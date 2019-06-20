package Projekti;

import javafx.application.Application;
import javafx.stage.Stage;

public class primaryClass extends Application {
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	
	public static Stage primaryStage;
	@Override
	public void start(Stage stage)
	{
		primaryClass.primaryStage=stage;
		//index.index();
		stage.setResizable(false);
		stage.show();
	}
}
