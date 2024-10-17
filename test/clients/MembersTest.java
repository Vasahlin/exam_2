package clients;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Notes: A membership is considered active if time since last payment < 365 days.
 */
class MembersTest {

    ArrayList<GymMember> members = new ArrayList<>(Arrays.asList(
            new GymMember("Alhambra Aromes", 7703021234L, LocalDate.parse("2024-07-01")),
            new GymMember("Bear Belle", 8204021234L, LocalDate.parse("2019-12-02")),
            new GymMember("Chamade Coriola", 8512021234L, LocalDate.parse("2018-03-12"))));
    Members m = new Members(members);


    @Test
    void updateMembership() {
        Members test = new Members(m.getMemberList());
        assertFalse(test.getMember(1).activeMembership);
        test.getMember(1).addPayment(LocalDate.parse("2024-07-01"));
        test.updateMemberships();
        assertTrue(test.getMember(1).activeMembership);
    }

    @Test
    void activeMembership() {
        assertTrue(m.getMember(0).activeMembership);
        assertFalse(m.getMember(1).activeMembership);
        assertFalse(m.getMember(2).activeMembership);
    }

    @Test
    void activeMember() {
        assertTrue(m.activeMember("Alhambra Aromes"));
        assertTrue(m.activeMember("ALHAMBRA AROMES"));
        assertTrue(m.activeMember(7703021234L));
        assertFalse(m.activeMember("Bear Belle"));
        assertFalse(m.activeMember("bear belle"));
        assertFalse(m.activeMember(8204021234L));
        assertFalse(m.activeMember("Chamade Coriola"));
        assertFalse(m.activeMember("cHamADE CORioLA"));
        assertFalse(m.activeMember(8512021234L));
    }

    @Test
    void memberIndex() {
        assertEquals(0,m.getMemberIndex("Alhambra Aromes"));
        assertEquals(0,m.getMemberIndex("ALHAMBRA AROMES"));
        assertEquals(0,m.getMemberIndex(7703021234L));
        assertEquals(1,m.getMemberIndex("Bear Belle"));
        assertEquals(1,m.getMemberIndex("bear belle"));
        assertEquals(1,m.getMemberIndex(8204021234L));
        assertEquals(2,m.getMemberIndex("Chamade Coriola"));
        assertEquals(2,m.getMemberIndex("cHamADE CORioLA"));
        assertEquals(2,m.getMemberIndex(8512021234L));
        assertEquals(-1,m.getMemberIndex("Not a Member"));
        assertEquals(-1,m.getMemberIndex(1000000000L));
    }

    @Test
    void exist() {
        assertTrue(m.memberExist("Alhambra Aromes"));
        assertTrue(m.memberExist("ALHAMBRA AROMES"));
        assertTrue(m.memberExist(7703021234L));
        assertTrue(m.memberExist("Bear Belle"));
        assertTrue(m.memberExist("bear belle"));
        assertTrue(m.memberExist(8204021234L));
        assertTrue(m.memberExist("Chamade Coriola"));
        assertTrue(m.memberExist("cHamADE CORioLA"));
        assertTrue(m.memberExist(8512021234L));
    }

    @Test
    void addMemberTestSortedByLong() {
        Members test = new Members(m.getMemberList());
        assertEquals("Alhambra Aromes", test.getMember(0).getName());
        test.addMember(new GymMember("First", 7512021234L, LocalDate.parse("2018-03-12")));
        assertEquals("First", test.getMember(0).getName());
    }
}