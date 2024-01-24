package dw.test.chapter1.item3;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import dw.chapter1.item3.Elvis;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ElvisTest {

    @Test
    void singleTonTest() {
        Elvis elvis = Elvis.INSTANCE;
        Elvis elvis2 = Elvis.INSTANCE;

        assertEquals(elvis, elvis2);
    }

    @Test
    void reflectionBreakSingleton()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Elvis elvis = Elvis.INSTANCE;
        Constructor<Elvis> constructor = (Constructor<Elvis>) elvis.getClass().getDeclaredConstructor();
        constructor.setAccessible(true);

        Elvis elvis2 = constructor.newInstance();
        assertNotSame(elvis, elvis2);
    }
}
