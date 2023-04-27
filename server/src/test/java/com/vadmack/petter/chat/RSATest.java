package com.vadmack.petter.chat;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSATest {

  private static final String SEED = "seed";
  private static final String ALGORITHM = "RSA";
  private static final String SECURITY_PROVIDER = "BC";

  @BeforeAll
  static void beforeAll() {
    Security.addProvider(new BouncyCastleProvider());
  }

  @Test
  void encodeAndDecodePublicKey() throws Exception{
    KeyPair pair = RSAUtils.getKeyPair(SEED);
    String publicKeyStr = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
    KeyFactory factory = KeyFactory.getInstance(ALGORITHM, SECURITY_PROVIDER);
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
  void tests() throws Exception {
    String msg = "String I want to encrypt";
    KeyPair pair = RSAUtils.getKeyPair(SEED);
    Cipher encryptCipher = Cipher.getInstance(ALGORITHM, SECURITY_PROVIDER);

    KeyFactory factory = KeyFactory.getInstance(ALGORITHM, SECURITY_PROVIDER);
    String publicKeyStr = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
    publicKeyStr = publicKeyStr.substring(publicKeyStr.length() - 2) + "a";
    PublicKey publicKey = factory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)));


    encryptCipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
    byte[] secretMessageBytes = msg.getBytes(StandardCharsets.UTF_8);
    byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
    Cipher decryptCipher = Cipher.getInstance(ALGORITHM, SECURITY_PROVIDER);
    decryptCipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
    byte[] decryptedMessageBytes = decryptCipher
            .doFinal(Base64.getDecoder().decode(Base64.getEncoder()
                    .encodeToString(encryptedMessageBytes)));

    String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
    assertEquals(decryptedMessage, msg);
  }
}
