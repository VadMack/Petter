package com.vadmack.petter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

@SpringBootApplication
public class PetterApplication {

  public static void main(String[] args) {
    Security.addProvider(new BouncyCastleProvider());
    SpringApplication.run(PetterApplication.class, args);
  }

}
