package com.app.Atom.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CartDB {

	public static int setcart(int listid, String cartname) {
		 int cartid=0;
		try {
          
			ResultSet rs;
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn
					.prepareStatement("insert into cart(cartname,listid)values(?,?) returning cartid");
			
			stmt.setString(1, cartname);
			stmt.setInt(2, listid);
			rs = stmt.executeQuery();
           while(rs.next()){
        	cartid=rs.getInt(1);
           }
			st.close();
			conn.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
		return cartid;
	}

	public static void deletelist(int cartid) {
		try {

			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("delete from cart where cartid=?");
			stmt.setInt(1, cartid);
			stmt.executeUpdate();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ee");
		}
		
		
	}

	public static void updatelist(int putcartid, String cartname) {
		try {

			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("update list set cartname=? where list.cartid=?");
			stmt.setString(1, cartname);
			stmt.setInt(2, putcartid);
			stmt.executeUpdate();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
		
	}

