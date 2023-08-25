package com.sumber.barokah.jurnal.condition;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Condition1Test {

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
