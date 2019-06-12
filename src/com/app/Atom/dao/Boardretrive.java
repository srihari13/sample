package com.app.Atom.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Boardretrive {
 public Map<Listretrive, List<Cartretrive>> getListAndCardDetails() {
		return listAndCardDetails;
	}
	public void setListAndCardDetails(Map<Listretrive, List<Cartretrive>> listAndCardDetails) {
		this.listAndCardDetails = listAndCardDetails;
	}
@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Boardid;
		result = prime * result + ((Boardname == null) ? 0 : Boardname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boardretrive other = (Boardretrive) obj;
		if (Boardid != other.Boardid)
			return false;
		if (Boardname == null) {
			if (other.Boardname != null)
				return false;
		} else if (!Boardname.equals(other.Boardname))
			return false;
		return true;
	}
public int getBoardid() {
		return Boardid;
	}
	public void setBoardid(int boardid) {
		Boardid = boardid;
	}
	public String getBoardname() {
		return Boardname;
	}
	public void setBoardname(String boardname) {
		Boardname = boardname;
	}
private int Boardid;
 private String Boardname;
 
 private Map<Listretrive,List<Cartretrive>> listAndCardDetails = new LinkedHashMap<>();
 
 


}
