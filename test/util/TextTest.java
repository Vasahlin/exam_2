package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextTest {

    @Test
    void removeHyphen() {

        String expectedSocial = "8708231325";
        String social = "870823-1325";
        assertEquals(expectedSocial, Text.removeHyphen(social));
        assertThrows(IllegalArgumentException.class, () ->
                Text.removeHyphen(""));
        assertThrows(IllegalArgumentException.class, () ->
                Text.removeHyphen("12345"));
        assertThrows(IllegalArgumentException.class, () ->
                Text.removeHyphen("12345-45467-1233"));
    }

    @Test
    void testRemoveHyphen() {
        String expected = "870823-1234";
        String result = Text.addHyphen(8708231234L);
        assertEquals(expected, result);
        expected = "010823-1234";
        result = Text.addHyphen(108231234L);
        assertEquals(expected, result);
    }
}
