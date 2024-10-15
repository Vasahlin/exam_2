import clients.GymMember;
import workoutTracking.FileHandling;
import workoutTracking.Workout;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        LinkedList<LocalDateTime> list = new LinkedList<>(List.of(LocalDateTime.now()));
        GymMember m = new GymMember("asd", "XXXX-XXXX", list);
        FileHandling.appendEntry(m);
    }
}