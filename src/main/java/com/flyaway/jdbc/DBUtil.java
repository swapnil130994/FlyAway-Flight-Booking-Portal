package com.flyaway.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtil {
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			System.out.println("Inside DbUtil.close(conn, stmt, rs)...");
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close(); //returns it to the connection pool for use
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
