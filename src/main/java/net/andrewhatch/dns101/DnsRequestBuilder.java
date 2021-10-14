package net.andrewhatch.dns101;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class DnsRequestBuilder {
  private final InetAddress dnsServerAddress;
  private final int port;

  public DnsRequestBuilder(final InetAddress dnsServerAddress, final int port) {
    this.dnsServerAddress = dnsServerAddress;
    this.port = port;
  }

  final DatagramPacket build(
      byte[] dnsFrame
  ) {
    return new DatagramPacket(
        dnsFrame,
        dnsFrame.length,
        this.dnsServerAddress,
        this.port
    );
  }
}
