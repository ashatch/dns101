package net.andrewhatch.dns101;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

public class RequestByteBuilder {
  private static final short QUERY_FLAGS = 0x0120;
  private static final short QUESTION_COUNT = 0x0001;
  private static final short ANSWER_COUNT = 0x0000;
  private static final short AUTHORITY_RECORD_COUNT = 0x0000;
  private static final short ADDITIONAL_RECORD_COUNT = 0x0000;
  private static final byte NO_MORE_PARTS = 0x00;
  public static final short TYPE_HOST_REQUEST = 0x0001;

  // IN denotes INTERNET
  private static final short CLASS_IN = 0x0001;

  private final String domain;
  private final Random random;

  public RequestByteBuilder(final String domain) {
    this.random = new Random();
    this.domain = domain;
  }

  public Optional<byte[]> getBytes() {
    try {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final DataOutputStream outputStream = new DataOutputStream(baos);
      outputStream.writeShort(randomIdField());
      outputStream.writeShort(QUERY_FLAGS);
      outputStream.writeShort(QUESTION_COUNT);
      outputStream.writeShort(ANSWER_COUNT);
      outputStream.writeShort(AUTHORITY_RECORD_COUNT);
      outputStream.writeShort(ADDITIONAL_RECORD_COUNT);
      this.writeDomainRequest(outputStream);
      outputStream.writeByte(NO_MORE_PARTS);
      outputStream.writeShort(TYPE_HOST_REQUEST);
      outputStream.writeShort(CLASS_IN);

      return Optional.of(baos.toByteArray());
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  private short randomIdField() {
    return (short) random.nextInt(Short.MAX_VALUE + 1);
  }

  private void writeDomainRequest(
      final DataOutputStream outputStream
  ) throws IOException {
    final String[] domainParts = this.domain.split("\\.");

    for (final String domainPart : domainParts) {
      final byte[] domainBytes = domainPart.getBytes(StandardCharsets.UTF_8);
      outputStream.writeByte(domainBytes.length);
      outputStream.write(domainBytes);
    }
  }
}
