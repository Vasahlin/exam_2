package clients;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
        Members test = new Members(m.memberList());
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
        assertTrue(m.activeMember(7703021234L));
        assertFalse(m.activeMember(8204021234L));
        assertFalse(m.activeMember(8512021234L));
    }

    @Test
    void memberIndex() {
        Members testList = new Members(m.memberList());
        assertEquals(0, testList.getMemberIndex(7703021234L));
        assertEquals(1, testList.getMemberIndex(8204021234L));
        assertEquals(2, testList.getMemberIndex(8512021234L));
        assertEquals(-1, testList.getMemberIndex(1000000000L));
        testList.addMember(new GymMember("First", 7512021234L, LocalDate.parse("2018-03-12")));
        assertEquals(0, testList.getMemberIndex(7512021234L));
    }

    @Test
    void exist() {
        assertTrue(m.memberExist(7703021234L));
        assertTrue(m.memberExist(8204021234L));
        assertTrue(m.memberExist(8512021234L));
    }

    @Test
    void addMemberTestSortedByLong() {
        Members test = new Members(m.memberList());
        assertEquals("Alhambra Aromes", test.getMember(0).getName());
        test.addMember(new GymMember("First", 7512021234L, LocalDate.parse("2018-03-12")));
        assertEquals("First", test.getMember(0).getName());
    }

    @Test
    void getMembersByName() {
        GymMember m1 = new GymMember(
                "Alhambra Aromes", 7703021234L, LocalDate.parse("2024-07-01"));
        GymMember m2 = new GymMember(
                "Alhambra Aromes", 7703024321L, LocalDate.parse("2024-07-01"));
        ArrayList<GymMember> expected = new ArrayList<>(Arrays.asList(m1,m2));
        expected.sort(Comparator.comparing(Person::getName));

        Members testList = new Members(m.memberList());
        testList.addMember(new GymMember(
                "Alhambra Aromes", 7703024321L, LocalDate.parse("2024-07-01")));

        ArrayList<GymMember> result = testList.getMembersByName("Alhambra Aromes");
        result.sort(Comparator.comparing(Person::getName));

        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), result.get(i).getName());
            assertEquals(expected.get(i).getSocialSecurity(), result.get(i).getSocialSecurity());
            assertEquals(expected.get(i).getPaymentHistory(), result.get(i).getPaymentHistory());
        }
    }
}