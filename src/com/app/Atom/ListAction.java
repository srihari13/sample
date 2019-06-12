package com.app.Atom;
import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

import com.app.Atom.dao.ListDB;
import com.opensymphony.xwork2.ActionSupport;

public class ListAction extends ActionSupport implements  ServletRequestAware,ServletResponseAware{
		private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		response=arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
	request=arg0;
		
	}

	public String execute(){
		String method=request.getMethod();
		switch(method){
		case "POST":
			System.out.println("insidepostcall");
			try{
			StringBuilder builder =new StringBuilder();
			BufferedReader bufferedReader =  request.getReader() ;
	        char[] charBuffer = new char[128];
	        int bytesRead;
	        while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
	        	builder.append(charBuffer, 0, bytesRead);
	        }
	        JSONObject jsobj=new JSONObject(builder.toString());
	        int boardid=jsobj.getInt("boardid");
	        String listname=jsobj.getString("listname");
			int listid=ListDB.setList(boardid,listname);
			  JSONObject jsobj1=new JSONObject();
			  jsobj1.put("listid", listid);
			response.getWriter().write(jsobj1.toString());
			}
			catch(Exception e){
				System.out.println(e);
			}
			break;
		case "GET":
			break;
		case "PUT":
			int Putlistid=Integer.parseInt(request.getParameter("listid"));
		     String listname=request.getParameter("listid");
			ListDB.updatelist(Putlistid,listname);
			break;
		case "DELETE":
			int listid=Integer.parseInt(request.getParameter("listid"));
			ListDB.deletelist(listid);
			break;	
		}
	
	return null;
    
	
		
	}

}
