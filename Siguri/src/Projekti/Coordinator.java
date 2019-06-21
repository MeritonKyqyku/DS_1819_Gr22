package Projekti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.Scene;



public class Coordinator {
	
	public static Connection DBConnection;
	public static Connection getDBConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DBConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Punetoret?useTimezone=true&serverTimezone=UTC","root","65280");
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
			String dbPassword="65280";
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Punetoret?useTimezone=true&serverTimezone=UTC",dbUser,dbPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setLoginPane() throws IOException
	{
		Scene klienti=new Scene(new Klienti());
		primaryClass.primaryStage.setScene(klienti);
	}
	
	public static void setAdminPane() throws IOException
	{
		Scene admin=new Scene(new memberespane());
		primaryClass.primaryStage.setScene(admin);
	}

}
