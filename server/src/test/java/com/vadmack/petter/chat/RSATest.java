package com.vadmack.petter.chat;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSATest {

  private static final String SEED = "seed";
  private static final String ALGORITHM = "RSA";

  @Test
  void encodeAndDecodePublicKey() throws Exception{
    KeyPair pair = RSAUtils.getKeyPair(SEED);
    String publicKeyStr = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
    KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
    PublicKey publicKey = factory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)));
    assertEquals(pair.getPublic(), publicKey);
  }

  @Test
  void encryptAndDecryptMsg() {
    String secretMessage = "String I want to encrypt";
    String encodedMessage = RSAUtils.encrypt(secretMessage, SEED);
    String decryptedMessage = RSAUtils.decrypt(encodedMessage, SEED);
    assertEquals(secretMessage, decryptedMessage);
  }

  @Test
  void decryptMsg() throws Exception{
    String secretMessage = "Hello";
    String chatRoomId = "6446d01e590c4a6970742751";
    String publicKeyStr = RSAUtils.getPublicKey(chatRoomId);

    assertEquals("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5s0EtJK0Jv0RrR+w3L2uoc/O0bLA8Zy08S4Jko7JGyCmjL2lpJHXhYNZHgRmMuNWTO9Xhg38yY0YcZmP5O4NSWQUhLI1WMTY/Y+YVszp+FyDqDOmxx0QrIRkjMaMK5Z0Vh/Vrh+lM8CSRrzCEd3BENOobjoKcluLNO/EV7FjefpK0Bg5eeWCvTXHeIYWo0NKJvUDzSJpyCtFyJyhUrHMtcaf4fE5MKO3gZQgMzCxHOXkTr9rOWV74u7jCyaAKwzSZ9ipNje6D0Oh6PL+4tTgD4IZ3m4TOSmFSYEI0B4ky3m0KF+2Ci2Nju3/Pc9PovMr1a9CAkv2hcKRPf3jGMwNIwIDAQAB",
            publicKeyStr);

    KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
    Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
    encryptCipher.init(Cipher.ENCRYPT_MODE, factory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr))));
    byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
    byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

    String encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
    String expected = "lNqA1VF3udXW5s6KGCKNfYeB00XKjnZJk+xLqpV0c4Iy4sWHaQZaV2Zl74rysXvSn404nX9VXBmbPFg9IiYayCcd/McjxP43IMV1cwzvfH8aiCigo/j7LpKzbrso64YsWFwnvmyo1WtloyggNQ/h/gTeQ+2qkDp+GnUvSeLij7rKAiQdUipuGKGxLUZ8GvLZyKkh8wZR6F6cHilKB8RDkyTL5PyMpyECwaFN3/60y6t2dk7qBCywwYJlwl0zJY5Br30x+tzktaTMqep13B07L6HnOp6TKfC5JJzEFLBed40/elOgmjSlQW2aToZuVf1y193Qkzg+E7FokfkqKKcZZw==";

    //assertEquals(expected, encryptedMessage);

    String decryptedMessage = RSAUtils.decrypt(expected, "6446d01e590c4a6970742751");
    assertEquals(secretMessage, decryptedMessage);
  }
}
