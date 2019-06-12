package com.app.Atom.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListDB {

	public static int setList(int boardid, String listname) {
		 int listid=0;
		try {
          
			ResultSet rs;
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn
					.prepareStatement("insert into list(listname,boardid)values(?,?) returning listid");
			
			stmt.setString(1, listname);
			stmt.setInt(2, boardid);
			
			rs = stmt.executeQuery();
           while(rs.next()){
        	listid=rs.getInt(1);
           }
			st.close();
			conn.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
		return listid;
	}

	public static void deletelist(int listid) {
		try {

			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("delete from list where listid=?");
			stmt.setInt(1, listid);
			stmt.executeUpdate();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ee");
		}
		
	}

	public static void updatelist(int putlistid, String listname) {
		
		
	}

}
