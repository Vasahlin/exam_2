package workoutTracking;

import clients.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Workout implements Serializable{
    Person member;
    LinkedList<LocalDateTime> workoutHistory;

    public Workout(GymMember member, LinkedList<LocalDateTime> workoutHistory) {
        this.member = member;
        this.workoutHistory = workoutHistory;
    }
}
