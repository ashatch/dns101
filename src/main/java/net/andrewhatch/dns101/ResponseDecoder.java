package net.andrewhatch.dns101;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class ResponseDecoder {

  public static void printPacket(
      final DatagramPacket packet,
      byte[] buf
  ) throws IOException {
    System.out.println("\n\nReceived: " + packet.getLength() + " bytes");
    DataInputStream din = new DataInputStream(new ByteArrayInputStream(buf));
    System.out.println("Transaction ID: 0x" + String.format("%x", din.readShort()));
    System.out.println("Flags: 0x" + String.format("%x", din.readShort()));
    System.out.println("Questions: 0x" + String.format("%x", din.readShort()));
    System.out.println("Answers RRs: 0x" + String.format("%x", din.readShort()));
    System.out.println("Authority RRs: 0x" + String.format("%x", din.readShort()));
    System.out.println("Additional RRs: 0x" + String.format("%x", din.readShort()));

    int recLen;
    while ((recLen = din.readByte()) > 0) {
      byte[] record = new byte[recLen];

      for (int i = 0; i < recLen; i++) {
        record[i] = din.readByte();
      }

      System.out.println("Record: " + new String(record, "UTF-8"));
    }

    System.out.println("Record Type: 0x" + String.format("%x", din.readShort()));
    System.out.println("Class: 0x" + String.format("%x", din.readShort()));

    System.out.println("Field: 0x" + String.format("%x", din.readShort()));
    System.out.println("Type: 0x" + String.format("%x", din.readShort()));
    System.out.println("Class: 0x" + String.format("%x", din.readShort()));
    System.out.println("TTL: 0x" + String.format("%x", din.readInt()));

    short addrLen = din.readShort();
    System.out.println("Len: 0x" + String.format("%x", addrLen));

    System.out.print("Address: ");
    for (int i = 0; i < addrLen; i++ ) {
      System.out.print("" + String.format("%d", (din.readByte() & 0xFF)) + ".");
    }
  }
}
