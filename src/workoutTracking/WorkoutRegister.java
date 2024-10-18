package workoutTracking;

import clients.*;
import util.Text;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class WorkoutRegister implements Serializable{
    Person member;
    LinkedList<LocalDateTime> workoutHistory;

    public WorkoutRegister(GymMember member) {
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

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.member.getName()).append(", ").
                append(this.member.getSocialSecurity()).append(System.lineSeparator()).
                append("Visits:").append(System.lineSeparator());
        for (LocalDateTime time : this.workoutHistory) {
            builder.append(Text.formatTime(time)).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
