package clients;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GymMemberTest {

    @Test
    void updateMembership() {
        GymMember m = new GymMember(
                "John Smith", 7703021234L, LocalDate.of(2022,8,12));
        assertFalse(m.activeMembership);
        m.addPayment(LocalDate.of(2024,8,20));
        m.updateMembership();
        assertTrue(m.activeMembership);
    }
}