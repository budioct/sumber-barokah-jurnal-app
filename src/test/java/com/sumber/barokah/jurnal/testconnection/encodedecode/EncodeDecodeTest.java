package com.sumber.barokah.jurnal.testconnection.encodedecode;

import com.sumber.barokah.jurnal.utilities.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
public class EncodeDecodeTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void EncodeDecodeBCyrpt(){

//        String password = "rahasia";
//        String hashpw1 = passwordEncoder.encode(password);

        boolean checkpw = BCrypt.checkpw("rahasia", "$2a$10$CM3UsAi9Miyos5rPqImbquiFsfexVx26RoxtBYr7TQvnKaW.5OLcy");
//        System.out.println("password encode " + hashpw1);
        System.out.println("status password " + checkpw);

//        log.info("password Encode =  {}", hashpw1);


    }


}
