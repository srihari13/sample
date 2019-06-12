package com.app.Atom.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardDB {

	public static Boardretrive getBoardDetails(int userid, int boardid) {
		Boardretrive boardobj = new Boardretrive();
		Map<Listretrive, List<Cartretrive>> submap = new LinkedHashMap<>();

		try {

			ResultSet rs;
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement(
					"select board.boardname,list.listid,list.listname,cart.cartid,cart.cartname from board left join list on list.boardid=board.boardid left join cart on list.listid=cart.listid  where board.userid=? and board.boardid=?");
			stmt.setInt(1, userid);
			stmt.setInt(2, boardid);
			rs = stmt.executeQuery();

			boardobj.setBoardid(boardid);
			while (rs.next()) {
				boardobj.setBoardname(rs.getString(1));
				Listretrive listobj = new Listretrive();
				Cartretrive cartobj = new Cartretrive();
				listobj.setListid(rs.getInt(2));
				listobj.setListname(rs.getString(3));
				cartobj.setCartid(rs.getInt(4));
				cartobj.setCartname(rs.getString(5));
				submap = boardobj.getListAndCardDetails();
				if (submap.containsKey(listobj)) {
					List<Cartretrive> list = submap.get(listobj);
					list.add(cartobj);
					submap.put(listobj, list);
				} else {
					List<Cartretrive> list = new ArrayList<>();
					list.add(cartobj);
					submap.put(listobj, list);
				}
				boardobj.setListAndCardDetails(submap);
			}
			st.close();
			conn.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
		return boardobj;
	}

	/**
	 * @param boardid
	 * 
	 * Pass the boardId you want to delete
	 */
	public static void deleteBoard(int boardid) {

		try {

			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("delete from board where boardid=?");
			stmt.setInt(1, boardid);
			stmt.executeUpdate();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ee");
		}
	}
	public static void updateBoard(int boardid, String boardname) {

		try {

			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("update board set boardname=? where boardid=?");
			stmt.setString(1, boardname);
			stmt.setInt(2, boardid);
			stmt.executeUpdate();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static int setBoard(int id, String boardname) {
		int boardid = 0;
		try {
			ResultSet rs;

			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn
					.prepareStatement("insert into board(userid,boardname) values (?,?) RETURNING boardid");
			stmt.setInt(1, id);
			stmt.setString(2, boardname);
			rs = stmt.executeQuery();
			while (rs.next()) {
				boardid = rs.getInt(1);
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("boo---" + boardid);
		return boardid;

	}
	public static List<Boardretrive>  getAllBoards(int userid) {
		List<Boardretrive> brdlist=new ArrayList<>();
		try {
         
			ResultSet rs;
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5431/postgres", "postgres",
					"postgres");
			Statement st = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("select boardid,boardname from board where userid=?");
			stmt.setInt(userid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Boardretrive boardobj = new Boardretrive();
				boardobj.setBoardid(rs.getInt(1));
				boardobj.setBoardname(rs.getString(2));
				brdlist.add(boardobj);
			}
			st.close();
			conn.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return brdlist;
	}
}
