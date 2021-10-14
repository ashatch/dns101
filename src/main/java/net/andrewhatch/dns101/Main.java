package net.andrewhatch.dns101;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;

public class Main {

  public static void main(final String[] args) throws IOException {
    final InetAddress googleDnsIp = InetAddress.getByAddress(new byte[]{0x08, 0x08, 0x08, 0x08});
    final DnsRequestBuilder packetBuilder = new DnsRequestBuilder(googleDnsIp, 53);
    final RequestByteBuilder requestBuilder = new RequestByteBuilder("example.com");
    final DatagramSocket socket = new DatagramSocket();
    final ResponseReceiver responseReceiver = new ResponseReceiver(socket);

    final Thread receiverThread = new Thread(responseReceiver);
    receiverThread.start();

    Optional.of(requestBuilder)
        .flatMap(RequestByteBuilder::getBytes)
        .map(packetBuilder::build)
        .ifPresentOrElse(
            packet -> sendPacket(socket, packet),
            () -> System.out.println("Couldn't make a packet to send :("));
  }

  private static void sendPacket(
      final DatagramSocket socket,
      final DatagramPacket packet
  ) {
    try {
      socket.send(packet);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
