package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextTest {

    @Test
    void generateInstructions() {
        String expectedResult = """
                1. Register visit
                2. Add membership
                3. Add payment
                4. Show member information
                5. Exit program
                """;
        assertEquals(expectedResult, Text.generateMessage(Text.message.MAIN_MENU));
        expectedResult = "Enter name for member: ";
        assertEquals(expectedResult, Text.generateMessage(Text.message.MEMBER_NAME));
        expectedResult = "Enter social security for member: ";
        assertEquals(expectedResult, Text.generateMessage(Text.message.MEMBER_SOCIAL));
    }

    @Test
    void removeHyphen() {

        String expectedSocial = "8708231325";
        String social = "870823-1325";
        assertEquals(expectedSocial, Text.removeHyphen(social));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                Text.removeHyphen(""));
        e = assertThrows(IllegalArgumentException.class, () ->
                Text.removeHyphen("12345"));
        e = assertThrows(IllegalArgumentException.class, () ->
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
