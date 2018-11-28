package kr.or.ddit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import oracle.jdbc.pool.OracleDataSource;

public class ConnectionFactory {
	private static String url;
	private static String user;
	private static String password;
	private static String driverClassName;
	private static DataSource dataSource;
	
	static {
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver"); // 클래스를 메모리에 로드
			ResourceBundle bundle = ResourceBundle.getBundle("kr.or.ddit.db.dbInfo");
			driverClassName = bundle.getString("driverClassName");
			url = bundle.getString("url"); 
			user = bundle.getString("user");
			password = bundle.getString("password");
//			DriverManager(Simple JDBC)와 DataSource(Pooling)의 차이
//			Simple JDBC 방식 : Connection이 필요할 때 그 즉시 생성
//			Pooling 방식 : 미리 일정 갯수의 Connection을 생성하고,
//			  			   Pool을 통행 관리하다, 필요할 때 배분해서 사용
//			OracleDataSource oracleDS = new OracleDataSource();
//			oracleDS.setURL(url);
//			oracleDS.setUser(user);
//			oracleDS.setPassword(password);
//			dataSource = oracleDS;
			
			// DBMS에 종속되지 않는 풀링 시스템
			BasicDataSource basicDS = new BasicDataSource();
			basicDS.setDriverClassName(driverClassName);
			basicDS.setUrl(url);
			basicDS.setUsername(user);
			basicDS.setPassword(password);
			// 풀링정책 설정
			int initialSize = Integer.parseInt(bundle.getString("initialSize"));
			int maxActive = Integer.parseInt(bundle.getString("maxActive"));
			long maxWait = Long.parseLong(bundle.getString("maxWait"));
			basicDS.setInitialSize(initialSize); // 초기 커넥션 생성 갯수 설정
			basicDS.setMaxActive(maxActive); // 최대 커넥션 갯수 설정
			basicDS.setMaxWait(maxWait); // 최대 대기시간
			dataSource = basicDS;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static Connection getConnection() throws SQLException {
//		Connection conn = DriverManager.getConnection(url, user, password);
		Connection conn = dataSource.getConnection();
		return conn;
	}
}