package com.sumber.barokah.jurnal.testconnection.condition;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConditionTest {

    @Test
    void testCondition() {

        Long exist = 10L;
        Long notExist = null;

        if (!(exist == null)) {
            System.out.println("Exist"); // print
        }

        if (!(notExist == null)) {
            System.out.println("Exist");
        } else {
            System.out.println("Not Exist"); // print
        }

    }

}
