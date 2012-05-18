package de.uxnr.ts3.api;

import java.io.Serializable;
import java.util.Vector;

import de.uxnr.ts3.net.ServerQuery;
import de.uxnr.ts3.net.ServerQueryResponse;
import de.uxnr.ts3.util.StringMap;

public class Connection implements Serializable {
	private static final long serialVersionUID = 1082719719206475541L;
	private ServerQuery sq;

	public Connection(String hostname, int port) {
		this.sq = new ServerQuery(hostname, port);
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode() ^ this.sq.hashCode();
	}

	public String getHelp() {
		return this.sq.execute("help");
	}

	public StringMap performLogin(String username, String password) {
		StringMap args = new StringMap();
		args.set("client_login_name", username);
		args.set("client_login_password", password);
		ServerQueryResponse resp = this.sq.query("login", args);
		if (resp.isError()) {
			resp.throwError();
		}
		return resp.getError();
	}

	public StringMap performLogout() {
		ServerQueryResponse resp = this.sq.query("logout");
		if (resp.isError()) {
			resp.throwError();
		}
		return resp.getError();
	}

	public StringMap getVersion() {
		return this.sq.query("version").getResponse()[0];
	}

	public StringMap getHostInfo() {
		return this.sq.query("hostinfo").getResponse()[0];
	}

	public StringMap getInstanceInfo() {
		return this.sq.query("instanceinfo").getResponse()[0];
	}

	public Instance getInstance() {
		return new Instance(this.sq, this.getInstanceInfo());
	}

	public StringMap[] getServerList() {
		return this.sq.query("serverlist").getResponse();
	}

	public Vector<Server> getServers() {
		Vector<Server> servers = new Vector<Server>();
		for (StringMap item : this.getServerList()) {
			servers.add(new Server(this.sq, item));
		}
		return servers;
	}

	public StringMap getWhoami() {
		return this.sq.query("whoami").getResponse()[0];
	}

	public User getUser() {
		return new User(this.sq, this.getWhoami());
	}

	public boolean isAuthorized() {
		return this.getUser().getClientUniqueIdentifier() != null;
	}
}
