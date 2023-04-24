package com.vadmack.petter.chat;

import com.vadmack.petter.app.exception.ServerSideException;
import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class RSAUtils {

  private static final String ALGORITHM = "RSA";

  public static KeyPair getKeyPair(String chatRoomId) {
    try {
      byte[] seed = chatRoomId.getBytes(UTF_8);
      SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
      rnd.setSeed(seed);
      RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4);
      KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
      pairGenerator.initialize(spec, rnd);
      return pairGenerator.generateKeyPair();
    } catch (GeneralSecurityException ex) {
      throw new ServerSideException("An error occurred during message encryption");
    }
  }

  public static String getPublicKey(String chatRoomId) {
    KeyPair pair = getKeyPair(chatRoomId);
    return Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
  }

  public static String encrypt(String msg, String chatRoomId) {
    try {
      KeyPair pair = RSAUtils.getKeyPair(chatRoomId);
      Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
      encryptCipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
      byte[] secretMessageBytes = msg.getBytes(StandardCharsets.UTF_8);
      byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
      return Base64.getEncoder().encodeToString(encryptedMessageBytes);
    } catch (GeneralSecurityException ex) {
      throw new ServerSideException("An error occurred during message encryption: " + ex);
    }
  }

  public static String decrypt(String encodedMessage, String chatRoomId) {
    try {
      PrivateKey privateKey = getKeyPair(chatRoomId).getPrivate();
      Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
      decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(encodedMessage));
      return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
    } catch (GeneralSecurityException ex) {
      throw new ServerSideException("An error occurred during message decryption: " + ex);
    }
  }
}
