package clients;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class GymMember extends Person implements Serializable {
    LinkedList<LocalDateTime> workoutHistory;

    public GymMember(String name, String socialSecurity, LinkedList<LocalDateTime> workoutHistory) {
        super(name, socialSecurity);
        this.workoutHistory = workoutHistory;
    }

    public LinkedList<LocalDateTime> getWorkoutHistory() {
        return workoutHistory;
    }
}
