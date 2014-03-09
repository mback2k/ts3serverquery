package de.uxnr.ts3.net;

public class ServerQueryError extends Error {
  private static final long serialVersionUID = -6427681942701229634L;
  private int id;
  private String msg;

  public ServerQueryError(int id, String msg) {
    this.id = id;
    this.msg = msg;
  }

  public int getID() {
    return this.id;
  }

  public String getMessage() {
    return this.msg;
  }

  public int hashCode() {
    return this.getClass().hashCode() ^ this.msg.hashCode() ^ this.id;
  }
}
