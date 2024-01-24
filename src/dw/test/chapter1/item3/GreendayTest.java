package dw.test.chapter1.item3;

import static org.junit.jupiter.api.Assertions.*;

import dw.chapter1.item3.Greenday;
import org.junit.jupiter.api.Test;

class GreendayTest {

    @Test
    void singletonTest() {
        Greenday greenday1 = Greenday.getInstance();
        Greenday greenday2 = Greenday.getInstance();

        assertNotEquals(greenday1, greenday2);
    }
}
