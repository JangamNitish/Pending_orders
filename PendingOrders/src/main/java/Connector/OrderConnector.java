package Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderConnector
{
	Connection conn;
	DataSource ds;


	public Connection getConnection() throws SQLException, NamingException, ClassNotFoundException {

		
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/orders", "root", "Nitish@1314");
		return conn;
	}
}
