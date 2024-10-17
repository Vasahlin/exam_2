package workoutTracking;

import clients.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Workout implements Serializable{
    Person member;
    LinkedList<LocalDateTime> workoutHistory;

    public Workout(GymMember member) {
        this.member = member;
        this.workoutHistory = member.getWorkoutHistory();
    }

    public String getNameOfClient() {
        return this.member.getName();
    }

    public long getSocialOfClient() {
        return this.member.getSocialSecurity();
    }

    public LinkedList<LocalDateTime> getWorkoutHistory() {
        return this.workoutHistory;
    }

}
