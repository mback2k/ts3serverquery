package de.uxnr.ts3.api;

import java.io.Serializable;


import de.uxnr.ts3.net.ServerQuery;
import de.uxnr.ts3.util.StringMap;

public class User implements Serializable {
	private static final long serialVersionUID = -616430763190183753L;
	private ServerQuery sq;
	private StringMap item;
	
	public User(ServerQuery sq, StringMap item) {
		this.sq = sq;
		this.item = item;
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode() ^ this.getClientUniqueIdentifier().hashCode();
	}
	
	public String getClientUniqueIdentifier() {
		return this.item.get("client_unique_identifier");
	}
}