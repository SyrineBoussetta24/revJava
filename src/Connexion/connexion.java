package Connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connexion {
	private static String login = "root";
	private static String password = "";
	private static String url="jdbc:mysql://localhost/dep_DB";
	private static Connection cn = null;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Problème de chargement du Driver!");
			System.exit(1);
		}

		String url = "jdbc:mysql://localhost:3306/Dep_DB";
	
			 try {
				cn = DriverManager.getConnection(url,login,password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	public static Connection getConnection() {
		return cn;
	}
}