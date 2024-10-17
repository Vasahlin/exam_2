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
            "%s\\test\\workoutTracking\\testFile", System.getProperty("user.dir"));

    @Test
    void serializationTest() throws IOException, ClassNotFoundException {
        Path testPath = Paths.get(fileName);
        FileHandling.test = true;
        try {
            GymMember toSerialize = new GymMember(
                    "John Smith", 7703021234L, LocalDate.of(2024,8,12));
            toSerialize.addWorkout(LocalDateTime.of(2024, 8,20,12,30));

            long writtenBytes = FileHandling.appendWorkoutToFile(toSerialize, testPath);
            assertTrue(writtenBytes != -1);

            Workout deserialized = FileHandling.readWorkoutFromFile(0, testPath);

            assertEquals(toSerialize.getName(), deserialized.getNameOfClient());
            assertEquals(toSerialize.getSocialSecurity(), deserialized.getSocialOfClient());
            assertEquals(toSerialize.getWorkoutHistory(), deserialized.getWorkoutHistory());
        } finally {
            Files.deleteIfExists(Paths.get(testPath + FileHandling.INDEX_EXT));
            Files.deleteIfExists(Paths.get(testPath + FileHandling.DATA_EXT));
        }
    }
}