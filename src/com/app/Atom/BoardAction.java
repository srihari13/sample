package com.app.Atom;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.Atom.dao.BoardDB;
import com.app.Atom.dao.Boardretrive;
import com.app.Atom.dao.Cartretrive;
import com.app.Atom.dao.Listretrive;
import com.opensymphony.xwork2.ActionSupport;

public class BoardAction extends ActionSupport implements  ServletRequestAware,ServletResponseAware{
		private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	String Username,Password;

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		response=arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
	request=arg0;
		
	}
	
	/*
 * 
{
"board":{
"name":"B1"
"list":[
{"name":"L1", "id":1},
{"name":"L1", "id":1}
{"name":"L1", "id":1}
]
}
} * */
	
	public String execute(){
	     HttpSession session=request.getSession(false);  
	      int userid=(int)session.getAttribute("userid");
		String method=request.getMethod();
		switch(method){
		case "POST":
			try {
				StringBuilder builder =new StringBuilder();
				BufferedReader bufferedReader =  request.getReader() ;
		        char[] charBuffer = new char[128];
		        int bytesRead;
		        while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
		        	builder.append(charBuffer, 0, bytesRead);
		        }
				JSONObject json = new JSONObject(builder.toString()).getJSONObject("board");
				 int boardid=BoardDB.setBoard(userid,json.getString("name"));
				 System.out.println("boo1--"+boardid);
				 JSONObject json1 = new JSONObject();
				 json1.put("boardid",boardid);
				response.getWriter().write(json1.toString());
			} catch (Exception e1) {
				e1.printStackTrace();	
			}
			break;
		case "GET":
			String boardid=request.getParameter("boardid");
			if(boardid!=null){
			int Bid=Integer.parseInt(boardid);
			Boardretrive bb;
		       Map<Listretrive, List<Cartretrive>> submap;
		       bb=BoardDB.getBoardDetails(userid,Bid);
		       try{
		    	   submap=bb.getListAndCardDetails();
		    	   JSONObject jsob=new JSONObject();
		    	
		    	   JSONArray jsobjArr=new JSONArray();
		    	   List<Cartretrive> list;
		    	         jsob.put("BoardId", bb.getBoardid());
		    	         jsob.put("BoardName", bb.getBoardname());
					      JSONObject jsobj=new JSONObject();
					      JSONArray jsobj1Arr=new JSONArray();
					for(Listretrive obj1:submap.keySet()){
				           list=submap.get(obj1);
				           JSONObject jsobj1=new JSONObject();
				           jsobj1.put("ListId",obj1.getListid());
				           jsobj1.put("ListName",obj1.getListname());
					
						JSONArray jsobj2Arr=new JSONArray();
						for(Cartretrive obj2:list){
							JSONObject jsobj2=new JSONObject();
							jsobj2.put("CardId",obj2.getCartid());
							jsobj2.put("CardName",obj2.getCartname());
							jsobj2Arr.put(jsobj2);
						}
						jsobj1.put("Card",jsobj2Arr);
						jsobj1Arr.put(jsobj1);
					}
						jsobj.put("List", jsobj1Arr);
						jsobjArr.put(jsobj);
						jsob.put("Board", jsobjArr);
						System.out.println(jsob.toString());
				  response.getWriter().write(jsob.toString());
		       }
		       catch(Exception e){
		    	   System.out.println(e);
		       }
			}
			else {
				try{
				JSONArray jsobj1Arr = new JSONArray();
				JSONObject jsobj = new JSONObject();
				List<Boardretrive> ls = BoardDB.getAllBoards();
				for (Boardretrive board : ls) {
					JSONObject jsob = new JSONObject();
						jsob.put("boardid", board.getBoardid());
						jsob.put("boardname", board.getBoardname());
						jsobj1Arr.put(jsob);
				}
				jsobj.put("Board", jsobj1Arr);
				response.getWriter().write(jsobj.toString());
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
			break;
		case "PUT":
			String name=request.getParameter("name");
			String boardidput=request.getParameter("boardid");
			int Bidput=Integer.parseInt(boardidput);
			System.out.println();
			BoardDB.updateBoard(Bidput,name);
			try {
				response.getWriter().write("Success");
			} catch (IOException e){
				e.printStackTrace();
			}
			break;
		case "DELETE":
			BoardDB.deleteBoard(4);
			break;	
		}
	
	return null;
    
	
		
	}

}
