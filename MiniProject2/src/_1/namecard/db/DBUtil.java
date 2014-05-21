package _1.namecard.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	// Db 연결
	public static Connection getConnection(){
		Connection conn = null;	//디비연결을 위한 참조변수 선언
		try {
			Class.forName("com.mysql.jdbc.Driver");	//드라이버로드
			String connectionURL ="jdbc:mysql://localhost:3306/mydb";  
			conn = DriverManager.getConnection(connectionURL,"root","0000");	//디비연결
		} catch (ClassNotFoundException e) {
			System.out.println("클래스가 없어요(com.mysql.jdbc.Driverr)");
		} catch (SQLException e) {
			System.out.println("SQL에러입니다.");
		}  
		return conn;
	}
	//db 닫기
	public static void close(Connection conn){
		try {
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			System.out.println("Connection Close Error");
		}
	}
	//statement 닫기
	public static void close(Statement stmt){
		try {
			if(stmt!=null) stmt.close();
		} catch (SQLException e) {
			System.out.println("Statement Close Error");
		}
	}
	//ResultSet
	public static void close(ResultSet rs){
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			System.out.println("ResultSet Close Error");
		}
	}
	// 롤백 
	public static void rollback(Connection conn){
		try {
			if(conn!=null) conn.rollback();
		} catch (SQLException e) {
			System.out.println("rollback Error");
		}
	}

}
