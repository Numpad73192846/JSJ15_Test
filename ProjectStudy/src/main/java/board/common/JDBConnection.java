package board.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnection {

	protected Connection con;
	protected PreparedStatement psmt;
	protected Statement stmt;
	protected ResultSet rs;
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url;
	private String user;
	private String password;
	
	public JDBConnection() {
		this.url = "jdbc:mysql://localhost:3306/study?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
		this.user = "aloha";
		this.password = "123456";
		connect();
	}
	
	public JDBConnection(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		connect();
	}
	
	protected void connect() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		
		if ( rs != null ) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( psmt != null ) {
			try {
				psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( stmt != null ) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( con != null ) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
