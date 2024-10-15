package clients;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class GymMember extends Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 2992501899333190796L;
    LinkedList<LocalDateTime> workoutHistory;
    LinkedList<LocalDate> paymentHistory;
    boolean activeMembership;

    public GymMember(String name, long socialSecurity, LocalDate firstPayment) {
        super(name, socialSecurity);
        this.paymentHistory = new LinkedList<LocalDate>();
        this.paymentHistory.add(firstPayment);
    }

    public void updateMembership() {
        if (paymentHistory.peekLast() == null) {
            throw new NullPointerException("Member has no registered payments");
        }
        this.activeMembership = ChronoUnit.DAYS.between(
                paymentHistory.peekLast(), LocalDate.now()) < 365;
    }

    public void addPayment(LocalDate timeOfPayment) {
        this.paymentHistory.add(timeOfPayment);
    }

    public LinkedList<LocalDate> getPaymentHistory() {
        return paymentHistory;
    }

    public void addWorkout(LocalDateTime timeOfWorkout) {
        if (this.workoutHistory == null) {
            this.workoutHistory = new LinkedList<>();
        }
        this.workoutHistory.add(timeOfWorkout);
    }

    public LinkedList<LocalDateTime> getWorkoutHistory() {
        return workoutHistory;
    }
}
