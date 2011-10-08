package de.uxnr.ts3.net;

import de.uxnr.ts3.util.StringMap;

public class ServerQueryResponse {
	private ServerQuery sq;
	private StringMap error = null;
	private StringMap[] response = null;
	
	public ServerQueryResponse(ServerQuery sq, String response) {
		this.sq = sq;
		this.parseResponse(response);
	}
	
	private void parseResponse(String response) {	
		String[] line = response.split("\n");
		if (line.length > 1) {
			String[] list = line[0].split("\\|");
			this.response = new StringMap[list.length];
			for (int i = 0; i < list.length; i++) {
				String[] item = list[i].split(" ");
				this.response[i] = new StringMap();
				for (int j = 0; j < item.length; j++) {
					this.parseValue(this.response[i], item[j]);
				}
			}
		}
		
		String[] error = line[line.length-1].split(" ");
		this.error = new StringMap();
		for (int i = 0; i < error.length; i++) {
			this.parseValue(this.error, error[i]);
		}
	}
	
	private void parseValue(StringMap map, String value) {
		String[] values = value.split("=", 2);
		if (values.length == 2) {
			map.set(values[0], this.sq.unescape(values[1]));
		} else {
			map.set(values[0], null);
		}
	}

	public ServerQueryError throwError() {
		throw new ServerQueryError(Integer.parseInt(this.error.get("id")), this.error.get("msg"));
	}
	
	public boolean isError() {
		return !this.error.get("id").equals("0");
	}
	
	public StringMap getError() {
		return this.error;
	}
	
	public StringMap[] getResponse() {
		return this.response;
	}
}
