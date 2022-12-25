package work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Loader {

	public static Connection loadSql() {
		String uname = "root", password = "root";
		String url = "jdbc:mysql://localhost:3306/CBTAPP";

		try {
			System.out.println("Hello...1");
			Connection conn = DriverManager.getConnection(url, uname, password);
			
			System.out.println("Hello...2");
			return conn;

		} catch (Exception e) {
			System.err.println("There was an error...: " + e.getMessage());
		}
		return null;
	}
	
}
