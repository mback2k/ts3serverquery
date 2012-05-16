package de.uxnr.ts3.api;

import java.io.Serializable;


import de.uxnr.ts3.net.ServerQuery;
import de.uxnr.ts3.net.ServerQueryResponse;
import de.uxnr.ts3.util.StringMap;

public class Server implements Serializable, Comparable<Server> {
	private static final long serialVersionUID = 1405369188208301208L;
	private ServerQuery sq;
	private StringMap item;
	
	public Server(ServerQuery sq, StringMap item) {
		this.sq = sq;
		this.item = item;
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode() ^ this.getID();
	}
	
	@Override
	public int compareTo(Server server) {
		return this.getID()-server.getID();
	}
	
	private String get(String property) {
		return this.item.get(property);
	}
	
	private void set(String property, String value) {
		StringMap args = new StringMap();
		args.set("sid", String.valueOf(this.getID()));
		this.sq.query("use", args);
		args = new StringMap();
		args.set(property, value);
		ServerQueryResponse resp = this.sq.query("serveredit", args);
		if (resp.isError()) {
			resp.throwError();
		}
		this.item.set(property, value);
	}
	
	public int getID() {
		return Integer.parseInt(this.get("virtualserver_id"));
	}
	
	public int getPort() {
		return Integer.parseInt(this.get("virtualserver_port"));
	}
	
	public String getStatus() {
		return this.get("virtualserver_status");
	}
	
	public int getClientsOnline() {
		return Integer.parseInt(this.get("virtualserver_clientsonline"));
	}
	
	public int getQueryClientsOnline() {
		return Integer.parseInt(this.get("virtualserver_queryclientsonline"));
	}
	
	public int getMaxClients() {
		return Integer.parseInt(this.get("virtualserver_maxclients"));
	}
	
	public int getUptime() {
		return Integer.parseInt(this.get("virtualserver_uptime"));
	}
	
	public String getName() {
		return this.get("virtualserver_name");
	}
	
	public void setName(String name) {
		this.set("virtualserver_name", name);
	}
	
	public boolean getAutostart() {
		return this.get("virtualserver_autostart").equals("1");
	}
	
	public void setAutostart(boolean autostart) {
		this.set("virtualserver_autostart", autostart ? "1" : "0");
	}
}
