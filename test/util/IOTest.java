package util;

import clients.GymMember;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IOTest {

    @Test
    void readFile() throws IOException {
        Path testDataPath = Paths.get(String.format("%s\\test\\util\\testData", System.getProperty("user.dir")));

        ArrayList<GymMember> testList = util.IO.readFile(testDataPath);
        ArrayList<GymMember> expectedList = new ArrayList<>(Arrays.asList(
                new GymMember("Alhambra Aromes", 7703021234L, LocalDate.parse("2024-07-01")),
                new GymMember("Bear Belle", 8204021234L, LocalDate.parse("2019-12-02")),
                new GymMember("Chamade Coriola", 8512021234L, LocalDate.parse("2018-03-12"))));

        assertEquals(expectedList.size(), testList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getName(), testList.get(i).getName());
            assertEquals(expectedList.get(i).getSocialSecurity(), testList.get(i).getSocialSecurity());
            assertEquals(expectedList.get(i).getPaymentHistory(), testList.get(i).getPaymentHistory());
        }
    }
}