package com.vadmack.petter.chat;

import org.junit.jupiter.api.Test;

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
}
