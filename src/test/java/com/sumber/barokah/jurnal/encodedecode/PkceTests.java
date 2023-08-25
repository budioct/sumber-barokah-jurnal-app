package com.sumber.barokah.jurnal.encodedecode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@SpringBootTest
public class PkceTests {

    @Test
    public void testGenerateCodeChallenge() throws Exception {
        String codeVerifier = "WGxFQsb57zOLAeXHW0Q6kR1dFHMFI66kAqx3_kGoqEYPAVtfyEZut1bfrhf9nTINzW0lCsoLjncNGXX2Ha-8Wm9SsL4Jv9faVkreIa1IC1HlF9Vh4EXTr6vWFU1UmUbE";
        String codeChallenge = calculateChallenge(codeVerifier);
        System.out.println("Verifier : ["+ codeVerifier +"]");
        System.out.println("Challenge : ["+codeChallenge +"]");
    }

    private String calculateChallenge(String codeVerifier) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

}
