package workoutTracking;

import clients.GymMember;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlingTest {
    final static String fileName = String.format(
            "%s\\test\\workoutTracking\\workoutTestList", System.getProperty("user.dir"));

    @Test
    void serializationTest() throws IOException, ClassNotFoundException {
        FileHandling.test = true;
        Path testPath = Paths.get(fileName);

        GymMember m = new GymMember(
                "John Smith", 7703021234L, LocalDate.of(2024,8,12));
        m.addWorkout(LocalDateTime.of(2024, 8,20,12,30));

        FileHandling.appendWorkoutToFile(m, testPath);
        Workout w = FileHandling.readWorkoutFromFile(0, testPath);

        assertEquals(m.getName(), w.getNameOfClient());
        assertEquals(m.getSocialSecurity(), w.getSocialOfClinet());
        assertEquals(m.getWorkoutHistory(), w.getWorkoutHistory());

        Files.deleteIfExists(Paths.get(testPath + FileHandling.INDEX_EXT));
        Files.deleteIfExists(Paths.get(testPath + FileHandling.DATA_EXT));
    }
}