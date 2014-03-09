package de.uxnr.ts3.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import de.uxnr.ts3.util.StringMap;

public class ServerQuery implements Serializable {
  private static final long serialVersionUID = -6310772699185695341L;
  private static final int ERROR_NO_TS3 = -1;
  private static final int ERROR_NO_HOST = -2;
  private static final int ERROR_NO_CONNECTION = -3;
  private Socket socket;
  private BufferedReader reader;
  private BufferedWriter writer;

  public ServerQuery(String hostname, int port) {
    try {
      this.socket = new Socket(hostname, port);
      this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
      if (this.readUntil("TS3")) {
        this.readUntil("ServerQuery");
      } else {
        throw new ServerQueryError(ERROR_NO_TS3, "This is not a Teamspeak 3 server.");
      }
    } catch (UnknownHostException e) {
      throw new ServerQueryError(ERROR_NO_HOST, "Could not find the host.");
    } catch (IOException e) {
      throw new ServerQueryError(ERROR_NO_CONNECTION, "Could not connect to the host.");
    }
  }

  public void finalize() {
    try {
      this.writeLine("quit");
      this.socket.close();
    } catch (IOException e) {
      return;
    }
  }

  public int hashCode() {
    return this.getClass().hashCode() ^ this.socket.hashCode();
  }

  private void writeLine(String line) throws IOException {
    this.writer.write(line);
    this.writer.write("\n");
    this.writer.flush();
  }

  private String readLine() throws IOException {
    return this.reader.readLine();
  }

  private String readResponse() throws IOException {
    String line = "";
    do {
      line += this.readLine();
      line += "\n";
    } while (!line.contains("error id="));
    this.readLine();
    return line;
  }

  private boolean readUntil(String search) throws IOException {
    while (this.socket.isConnected()) {
      String line = this.readLine();
      if (line != null && line.contains(search)) {
        this.readLine();
        return true;
      }
    }
    return false;
  }

  protected String escape(String text) {
    text = text.replace("\\", "\\\\");
    text = text.replace("/", "\\/");
    text = text.replace(" ", "\\s");
    text = text.replace("|", "\\p");
    text = text.replace("\b", "\\b");
    text = text.replace("\f", "\\f");
    text = text.replace("\n", "\\n");
    text = text.replace("\r", "\\r");
    text = text.replace("\t", "\\t");
    return text;
  }

  protected String unescape(String text) {
    text = text.replace("\\\\", "\\");
    text = text.replace("\\/", "/");
    text = text.replace("\\s", " ");
    text = text.replace("\\p", "|");
    text = text.replace("\\b", "\b");
    text = text.replace("\\f", "\f");
    text = text.replace("\\n", "\n");
    text = text.replace("\\r", "\r");
    text = text.replace("\\t", "\t");
    return text;
  }

  protected String execute(ServerQueryRequest request) throws ServerQueryError {
    try {
      this.writeLine(request.buildRequest());
    } catch (IOException e) {
      throw new ServerQueryError(-1, "Could not write request to buffered stream.");
    }
    try {
      return this.readResponse();
    } catch (IOException e) {
      throw new ServerQueryError(-1, "Could not read response from buffered stream.");
    }
  }

  public String execute(String command, StringMap parameters, String[] options)
      throws ServerQueryError {
    return this.execute(new ServerQueryRequest(this, command, parameters, options));
  }

  public String execute(String command, StringMap parameters) throws ServerQueryError {
    return this.execute(new ServerQueryRequest(this, command, parameters));
  }

  public String execute(String command) throws ServerQueryError {
    return this.execute(new ServerQueryRequest(this, command));
  }

  protected ServerQueryResponse query(ServerQueryRequest request) throws ServerQueryError {
    return new ServerQueryResponse(this, this.execute(request));
  }

  public ServerQueryResponse query(String command, StringMap parameters, String[] options)
      throws ServerQueryError {
    return this.query(new ServerQueryRequest(this, command, parameters, options));
  }

  public ServerQueryResponse query(String command, StringMap parameters) throws ServerQueryError {
    return this.query(new ServerQueryRequest(this, command, parameters));
  }

  public ServerQueryResponse query(String command) throws ServerQueryError {
    return this.query(new ServerQueryRequest(this, command));
  }
}
