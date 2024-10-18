package workoutTracking;

import clients.GymMember;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlingTest {
    final static String fileName = String.format(
            "%s\\test\\workoutTracking\\testFile", System.getProperty("user.dir"));

    @Test
    void updateWorkouts() throws IOException, ClassNotFoundException {
        Path indexPath = Paths.get(fileName + FileHandling.INDEX_EXT);
        Path dataPath = Paths.get(fileName + FileHandling.DATA_EXT);
        try {
            Path path = Paths.get(fileName);
            Files.createFile(indexPath);
            Files.createFile(dataPath);
            FileHandling.test = true;

            //Passing 1 object to empty list
            GymMember m_1 = new GymMember(
                    "John Smith", 4703021234L, LocalDate.of(2024, 8, 12));
            m_1.addWorkout(LocalDateTime.of(2023, 1, 1, 12, 30));
            WorkoutRegister w_1 = new WorkoutRegister(m_1);

            FileHandling.updateWorkouts(
                    m_1, LocalDateTime.of(2023, 1, 1, 12, 30), path);
            ArrayList<WorkoutRegister> result = FileHandling.readAllWorkouts(1, path);

            for (WorkoutRegister workout : result) {
                assertEquals(w_1.getWorkoutHistory(), workout.getWorkoutHistory());
                assertEquals(w_1.getSocialOfClient(), workout.getSocialOfClient());
                assertEquals(w_1.getNameOfClient(), workout.getNameOfClient());
            }

            //Expecting an updated object not a second element
            LocalDateTime time = LocalDateTime.of(2023, 1, 1, 12, 30);
            FileHandling.updateWorkouts(m_1, time, path);
            result = FileHandling.readAllWorkouts(FileHandling.amountOfElements(path), path);
            assertEquals(1, result.size());


        } finally {
            Files.deleteIfExists(indexPath);
            Files.deleteIfExists(dataPath);
        }
    }

    @Test
    void serializationTest() throws IOException, ClassNotFoundException {
        Path testPath = Paths.get(fileName);
        FileHandling.test = true;
        try {
            GymMember original = new GymMember(
                    "John Smith", 7703021234L, LocalDate.of(2024, 8, 12));
            original.addWorkout(LocalDateTime.of(2024, 8, 20, 12, 30));
            WorkoutRegister workout = new WorkoutRegister(original);

            long writtenBytes = FileHandling.appendWorkoutToFile(workout, testPath);
            assertTrue(writtenBytes != -1);

            WorkoutRegister deserialized = FileHandling.readWorkoutFromFile(0, testPath);

            assertEquals(original.getName(), deserialized.getNameOfClient());
            assertEquals(original.getSocialSecurity(), deserialized.getSocialOfClient());
            assertEquals(original.getWorkoutHistory(), deserialized.getWorkoutHistory());
        } finally {
            Files.deleteIfExists(Paths.get(testPath + FileHandling.INDEX_EXT));
            Files.deleteIfExists(Paths.get(testPath + FileHandling.DATA_EXT));
        }
    }

    @Test
    void readFromMember() throws IOException, ClassNotFoundException {
        Path indexPath = Paths.get(fileName + FileHandling.INDEX_EXT);
        Path dataPath = Paths.get(fileName + FileHandling.DATA_EXT);
        Path path = Paths.get(fileName);
        try {
            FileHandling.test = true;
            Files.createFile(indexPath);
            Files.createFile(dataPath);

            GymMember m_1 = new GymMember(
                    "John Smith", 4703021234L, LocalDate.of(2024, 8, 12));
            m_1.addWorkout(LocalDateTime.of(2023, 1, 1, 12, 30));
            FileHandling.updateWorkouts(
                    m_1, LocalDateTime.of(2023, 1, 1, 12, 30), path);
            WorkoutRegister w = FileHandling.readWorkoutFromFile(m_1.getFileIndex(), path);

            assertEquals(m_1.getSocialSecurity(), w.getSocialOfClient());
            assertEquals(m_1.getName(), w.getNameOfClient());
            assertEquals(m_1.getWorkoutHistory().size(), w.workoutHistory.size());
        } finally {
            Files.deleteIfExists(Paths.get(path + FileHandling.INDEX_EXT));
            Files.deleteIfExists(Paths.get(path + FileHandling.DATA_EXT));
        }
    }

    @Test
    void amountOfElements() throws IOException {
        Path testPath = Paths.get(fileName);
        FileHandling.test = true;
        Path path = Paths.get(testPath + FileHandling.INDEX_EXT);
        try {
            Files.createFile(path);
            long index = FileHandling.amountOfElements(testPath);
            assertEquals(0, index);
            Files.deleteIfExists(path);

            GymMember m = new GymMember(
                    "John Smith", 7703021234L, LocalDate.of(2024, 8, 12));
            m.addWorkout(LocalDateTime.of(2024, 8, 20, 12, 30));
            WorkoutRegister workout = new WorkoutRegister(m);

            long writtenBytes = FileHandling.appendWorkoutToFile(workout, testPath);
            assertTrue(writtenBytes != -1);

            index = FileHandling.amountOfElements(testPath);
            assertEquals(1, index);

            writtenBytes = FileHandling.appendWorkoutToFile(workout, testPath);
            assertTrue(writtenBytes != -1);

            index = FileHandling.amountOfElements(testPath);
            assertEquals(2, index);
        } finally {
            Files.deleteIfExists(path);
            Files.deleteIfExists(Paths.get(testPath + FileHandling.DATA_EXT));
        }
    }

    @Test
    void readAllWorkouts() throws IOException, ClassNotFoundException {
        Path testPath = Paths.get(fileName);
        FileHandling.test = true;
        try {
            GymMember m = new GymMember(
                    "John Smith", 7703021234L, LocalDate.of(2024, 8, 12));
            m.addWorkout(LocalDateTime.of(2024, 8, 20, 12, 30));
            WorkoutRegister w_1 = new WorkoutRegister(m);
            WorkoutRegister w_2 = new WorkoutRegister(m);
            WorkoutRegister w_3 = new WorkoutRegister(m);

            long writtenBytes = FileHandling.appendWorkoutToFile(w_1, testPath);
            assertTrue(writtenBytes != -1);
            writtenBytes = FileHandling.appendWorkoutToFile(w_2, testPath);
            assertTrue(writtenBytes != -1);
            writtenBytes = FileHandling.appendWorkoutToFile(w_3, testPath);
            assertTrue(writtenBytes != -1);

            w_1 = FileHandling.readWorkoutFromFile(0, testPath);

            long index = FileHandling.amountOfElements(testPath);
            ArrayList<WorkoutRegister> result = FileHandling.readAllWorkouts(index, testPath);
            ArrayList<WorkoutRegister> expected = new ArrayList<>(Arrays.asList(w_1, w_2, w_3));

            assertEquals(expected.size(), result.size());
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i).getNameOfClient(), result.get(i).getNameOfClient());
                assertEquals(expected.get(i).getSocialOfClient(), result.get(i).getSocialOfClient());
                assertEquals(expected.get(i).getWorkoutHistory(), result.get(i).getWorkoutHistory());
            }
        } finally {
            Files.deleteIfExists(Paths.get(testPath + FileHandling.INDEX_EXT));
            Files.deleteIfExists(Paths.get(testPath + FileHandling.DATA_EXT));
        }
    }

    @Test
    void writeAllWorkouts() throws IOException, ClassNotFoundException {
        Path testPath = Paths.get(fileName);
        FileHandling.test = true;
        try {
            //These should be in the result
            GymMember m_1 = new GymMember(
                    "John Smith", 4703021234L, LocalDate.of(2024, 8, 12));
            m_1.addWorkout(LocalDateTime.of(2023, 1, 1, 12, 30));
            WorkoutRegister w_1 = new WorkoutRegister(m_1);
            GymMember m_2 = new GymMember(
                    "Jane Smith", 5703021234L, LocalDate.of(2024, 8, 12));
            m_2.addWorkout(LocalDateTime.of(2023, 2, 2, 14, 40));
            m_2.addWorkout(LocalDateTime.of(2023, 3, 3, 14, 40));
            WorkoutRegister w_2 = new WorkoutRegister(m_1);
            GymMember m_3 = new GymMember(
                    "Jane Doe", 6703021234L, LocalDate.of(2024, 8, 12));
            m_3.addWorkout(LocalDateTime.of(2023, 4, 4, 14, 40));
            WorkoutRegister w_3 = new WorkoutRegister(m_3);

            //These should not be in the result
            GymMember m_4 = new GymMember(
                    "Smith John", 7703021234L, LocalDate.of(2024, 8, 12));
            m_1.addWorkout(LocalDateTime.of(2024, 5, 5, 12, 30));
            WorkoutRegister w_4 = new WorkoutRegister(m_4);
            GymMember m_5 = new GymMember(
                    "Smith Jane", 8703021234L, LocalDate.of(2024, 8, 12));
            m_2.addWorkout(LocalDateTime.of(2024, 6, 6, 14, 40));
            m_2.addWorkout(LocalDateTime.of(2024, 7, 7, 14, 40));
            WorkoutRegister w_5 = new WorkoutRegister(m_5);
            GymMember m_6 = new GymMember(
                    "Doe Jane", 9703021234L, LocalDate.of(2024, 8, 12));
            m_3.addWorkout(LocalDateTime.of(2024, 8, 8, 14, 40));
            WorkoutRegister w_6 = new WorkoutRegister(m_6);

            ArrayList<WorkoutRegister> expectedResult = new ArrayList<>(Arrays.asList(w_1, w_2, w_3));
            ArrayList<WorkoutRegister> shouldNotBeResult = new ArrayList<>(Arrays.asList(w_4, w_5, w_6));

            FileHandling.writeAllWorkouts(shouldNotBeResult, testPath);
            FileHandling.writeAllWorkouts(expectedResult, testPath);
            ArrayList<WorkoutRegister> result = FileHandling.readAllWorkouts(expectedResult.size(), testPath);

            result.sort(Comparator.comparingLong(WorkoutRegister::getSocialOfClient));
            expectedResult.sort(Comparator.comparingLong(WorkoutRegister::getSocialOfClient));

            assertEquals(expectedResult.size(), result.size());
            for (int i = 0; i < expectedResult.size(); i++) {
                assertEquals(expectedResult.get(i).getNameOfClient(), result.get(i).getNameOfClient());
                assertEquals(expectedResult.get(i).getSocialOfClient(), result.get(i).getSocialOfClient());
                assertEquals(expectedResult.get(i).getWorkoutHistory(), result.get(i).getWorkoutHistory());
            }
        } finally {
            Files.deleteIfExists(Paths.get(testPath + FileHandling.INDEX_EXT));
            Files.deleteIfExists(Paths.get(testPath + FileHandling.DATA_EXT));
        }
    }

    @Test
    void findIndex() {
        GymMember m_1 = new GymMember(
                "John Smith", 4703021234L, LocalDate.of(2024, 8, 12));
        m_1.addWorkout(LocalDateTime.of(2023, 1, 1, 12, 30));
        WorkoutRegister w_1 = new WorkoutRegister(m_1);
        GymMember m_2 = new GymMember(
                "Jane Smith", 5703021234L, LocalDate.of(2024, 8, 12));
        m_2.addWorkout(LocalDateTime.of(2023, 2, 2, 14, 40));
        WorkoutRegister w_2 = new WorkoutRegister(m_2);
        GymMember m_3 = new GymMember(
                "Jane Doe", 6703021234L, LocalDate.of(2024, 8, 12));
        m_3.addWorkout(LocalDateTime.of(2023, 4, 4, 14, 40));
        WorkoutRegister w_3 = new WorkoutRegister(m_3);

        ArrayList<WorkoutRegister> list = new ArrayList<>(Arrays.asList(w_1, w_2, w_3));
        int expected = 0;
        int result = FileHandling.registeredClientIndex(list, m_1.getSocialSecurity());
        assertEquals(expected, result);
    }
}
