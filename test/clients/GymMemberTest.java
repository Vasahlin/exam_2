package clients;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GymMemberTest {

    GymMember m = new GymMember(
            "John Smith", 7703021234L, LocalDate.of(2022,8,12));

    @Test
    void updateMembership() {
        assertFalse(m.activeMembership);
        m.addPayment(LocalDate.of(2024,8,20));
        m.updateMembership();
        assertTrue(m.activeMembership);
    }

    @Test
    void testToString() {
        String expected = "John Smith, 770302-1234" + System.lineSeparator() + "Membership status: false";
        assertEquals(expected, m.toString());
    }
}