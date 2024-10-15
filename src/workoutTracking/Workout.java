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
        this.workoutHistory = member.getWorkoutHistory();
    }

    public String getNameOfClient() {
        return this.member.getName();
    }

    public long getSocialOfClinet() {
        return this.member.getSocialSecurity();
    }

    public LinkedList<LocalDateTime> getWorkoutHistory() {
        return this.workoutHistory;
    }

}
