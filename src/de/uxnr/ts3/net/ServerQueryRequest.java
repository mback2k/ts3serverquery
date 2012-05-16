package de.uxnr.ts3.net;

import de.uxnr.ts3.util.StringMap;

public class ServerQueryRequest {
	private ServerQuery sq;
	private String command;
	private StringMap parameters = null;
	private String[] options = null;
	
	public ServerQueryRequest(ServerQuery sq, String command, StringMap parameters, String[] options) {
		this.sq = sq;
		this.command = command;
		this.parameters = parameters;
		this.options = options;
	}
	
	public ServerQueryRequest(ServerQuery sq, String command, StringMap parameters) {
		this.sq = sq;
		this.command = command;
		this.parameters = parameters;
		this.options = new String[0];
	}
	
	public ServerQueryRequest(ServerQuery sq, String command) {
		this.sq = sq;
		this.command = command;
		this.parameters = new StringMap();
		this.options = new String[0];
	}
	
	protected String buildRequest() {
		String request = this.command;
		String[] keys = this.parameters.keys();
		for (int i = 0; i < keys.length; i++) {
			request += " ";
			
			String[] values = this.parameters.getArray(keys[i]);
			for (int j = 0; j < values.length; j++) {
				if (j >= 1) {
					request += "|";
				}
				request += keys[i];
				request += "=";
				request += this.sq.escape(values[j]);
			}
		}
		for (int i = 0; i < this.options.length; i++) {
			request += " -";
			request += this.options[i];
		}
		return request;
	}
	
	public int hashCode() {
		return this.getClass().hashCode() ^ this.sq.hashCode() ^ this.command.hashCode() ^ this.parameters.hashCode() ^ this.options.hashCode();
	}
}
