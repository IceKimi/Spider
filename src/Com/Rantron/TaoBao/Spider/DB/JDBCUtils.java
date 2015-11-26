package Com.Rantron.TaoBao.Spider.DB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



public final class JDBCUtils {


	private static String driverClassName="com.mysql.jdbc.Driver";
	private static String url;
	private static String username;
	private static String password;
	private static String localurl;
	private static String localusername;
	private static String localpassword;

	static {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private JDBCUtils() {
		
		try {
			Properties properties = new Properties();

			InputStream inputStream =JDBCUtils.class.getResourceAsStream(
					"/jdbc.properties");
			properties.load(inputStream);
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
private JDBCUtils(String _url) {
		
		try {
			Properties properties = new Properties();

			InputStream inputStream =JDBCUtils.class.getResourceAsStream(
					"/jdbc.properties");
			properties.load(inputStream);
			url = _url;
			username = properties.getProperty("username");
			password = properties.getProperty("password");
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		new JDBCUtils();
		return DriverManager.getConnection(url, username, password);
	}
	
	public static Connection getConnection(String _url) throws SQLException {
		new JDBCUtils(_url);
		return DriverManager.getConnection(url, username, password);
	}
	
	public static Connection getLocalConnection() throws SQLException{
		new JDBCUtils();
		return DriverManager.getConnection(localurl, localusername, localpassword);
	}

	public static void free(ResultSet resultSet, Statement statement,
			Connection connection) {
		try {
			if(resultSet!=null){
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null){
					statement.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

