package com.app.Atom;
import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

import com.app.Atom.dao.CartDB;
import com.app.Atom.dao.ListDB;
import com.opensymphony.xwork2.ActionSupport;

public class cartAction extends ActionSupport implements  ServletRequestAware,ServletResponseAware{
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

	public String execute(){
		String method=request.getMethod();
		switch(method){
		case "POST":
			try{
			StringBuilder builder =new StringBuilder();
			BufferedReader bufferedReader =  request.getReader() ;
	        char[] charBuffer = new char[128];
	        int bytesRead;
	        while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
	        	builder.append(charBuffer, 0, bytesRead);
	        }
	        JSONObject jsobj=new JSONObject(builder.toString());
	        int listid=jsobj.getInt("listid");
	        String cartname=jsobj.getString("cartname");
			int cartid=CartDB.setcart(listid,cartname);
			  JSONObject jsobj1=new JSONObject();
			  jsobj1.put("cartid", cartid);
			response.getWriter().write(jsobj1.toString());
			}
			catch(Exception e){
				System.out.println(e);
			}
			break;
		case "GET":
			break;
		case "PUT":
			int Putcartid=Integer.parseInt(request.getParameter("listid"));
		     String cartname=request.getParameter("listid");
			CartDB.updatelist(Putcartid,cartname);
			break;
		case "DELETE":
			int cartid=Integer.parseInt(request.getParameter("cartid"));
			System.out.println(cartid);
			CartDB.deletelist(cartid);
			break;	
		}
	
	return null;
    
	
		
	}

}
