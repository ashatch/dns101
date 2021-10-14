package net.andrewhatch.dns101;

public class HexDump {
  public static String hexDump(final byte[] buf, final int len) {
    final StringBuilder hexDumpString = new StringBuilder();

    StringBuilder asciiConversion = new StringBuilder();

    for (int i = 0; i < len; i++) {
      final byte byteChar = buf[i];

      hexDumpString.append(hexFormat(byteChar));
      asciiConversion.append(printableChar(byteChar));

      if (isEndOfLine(i) || isEndOfBuffer(len, i)) {
        if (!isEndOfLine(i)) {
          addPadding(hexDumpString, asciiConversion, i);
        }
        hexDumpString.append(asciiConversion + "\n");
        asciiConversion = new StringBuilder();
      }
    }

    return hexDumpString.toString();
  }

  private static void addPadding(
      final StringBuilder hexDumpString,
      final StringBuilder asciiConversion,
      final int i
  ) {
    int padCount = 16 - ((i+1) % 16);
    hexDumpString.append("   ".repeat(padCount));
    asciiConversion.append(" ".repeat(padCount));
  }

  private static String hexFormat(byte byteChar) {
    return String.format("%02x ", byteChar);
  }

  private static char printableChar(byte byteChar) {
    return isPrintableChar(byteChar) ? (char) byteChar : '.';
  }

  private static boolean isPrintableChar(byte byteChar) {
    return byteChar >= 33 && byteChar <= 126;
  }

  private static boolean isEndOfBuffer(int len, int i) {
    return i == len-1;
  }

  private static boolean isEndOfLine(int i) {
    return (i+1) % 16 == 0;
  }
}
