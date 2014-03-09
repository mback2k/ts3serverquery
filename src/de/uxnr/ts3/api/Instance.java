package de.uxnr.ts3.api;

import java.io.Serializable;

import de.uxnr.ts3.net.ServerQuery;
import de.uxnr.ts3.util.StringMap;

public class Instance implements Serializable {
  private static final long serialVersionUID = -5137957374133459274L;
  private ServerQuery sq;
  private StringMap item;

  public Instance(ServerQuery sq, StringMap item) {
    this.sq = sq;
    this.item = item;
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode() ^ this.sq.hashCode();
  }

  public int getUptime() {
    return Integer.parseInt(this.item.get("instance_uptime"));
  }

  public int getHostTimestampUTC() {
    return Integer.parseInt(this.item.get("host_timestamp_utc"));
  }

  public int getVirtualServersRunningTotal() {
    return Integer.parseInt(this.item.get("virtualservers_running_total"));
  }

  public int getFiletransferBandwidthSent() {
    return Integer.parseInt(this.item.get("connection_filetransfer_bandwidth_sent"));
  }

  public int getFiletransferBandwidthReceived() {
    return Integer.parseInt(this.item.get("connection_filetransfer_bandwidth_received"));
  }

  public int getPacketsSentTotal() {
    return Integer.parseInt(this.item.get("connection_packets_sent_total"));
  }

  public int getPacketsReceivedTotal() {
    return Integer.parseInt(this.item.get("connection_packets_received_total"));
  }

  public int getBytesSentTotal() {
    return Integer.parseInt(this.item.get("connection_bytes_sent_total"));
  }

  public int getBytesReceivedTotal() {
    return Integer.parseInt(this.item.get("connection_bytes_received_total"));
  }

  public int getBandwidthSentLastSecondTotal() {
    return Integer.parseInt(this.item.get("connection_bandwidth_sent_last_second_total"));
  }

  public int getBandwidthReceivedLastSecondTotal() {
    return Integer.parseInt(this.item.get("connection_bandwidth_received_last_second_total"));
  }

  public int getBandwidthSentLastMinuteTotal() {
    return Integer.parseInt(this.item.get("connection_bandwidth_sent_last_minute_total"));
  }

  public int getBandwidthReceivedLastMinuteTotal() {
    return Integer.parseInt(this.item.get("connection_bandwidth_received_last_minute_total"));
  }
}
