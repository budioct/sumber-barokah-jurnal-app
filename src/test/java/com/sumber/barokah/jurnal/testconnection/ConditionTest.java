package com.sumber.barokah.jurnal.testconnection;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConditionTest {

    @Test
    void testCondition(){

        var data = "sepak bola";

        if (!(data == null)){
            System.out.println(true);
        } else {
            System.out.println(false);
        }

    }

}
