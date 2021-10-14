package net.andrewhatch.dns101;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static net.andrewhatch.dns101.ResponseDecoder.printPacket;

public class ResponseReceiver implements Runnable {
  private final DatagramSocket socket;

  public ResponseReceiver(final DatagramSocket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      final byte[] buf = new byte[1024];
      final DatagramPacket packet = new DatagramPacket(buf, buf.length);
      socket.receive(packet);

      printPacket(packet, buf);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
