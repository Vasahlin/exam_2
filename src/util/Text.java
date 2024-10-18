package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Text {

    public enum message {
        MAIN_MENU, ADD_MEMBER, ENTER_CHOICE,
        MEMBER_NAME, MEMBER_SOCIAL, MEMBER_ADDED,
        PAYMENT_MENU, PAYMENT_ADDED, SHOW_MEMBER_MENU,
        NAME_SOCIAL, NO_SUCH_MEMBER, REGISTER_VISIT,
        INVALID_FORMATTING, VISIT_HISTORY, NOT_ACTIVE,
        VISIT_REGISTERED, ALREADY_EXIST, NO_HISTORY

    }
    public final static String RED = "\033[31m";
    public final static String YEL = "\033[33m";
    public final static String RESET = "\033[0m";
    public final static String LS = System.lineSeparator();
    public final static String genericError = "Something went wrong. Please try again";

    public static String generateMessage(message option) {
        return switch (option) {
            case MAIN_MENU -> """
                    1. Register visit
                    2. Add membership
                    3. Add payment
                    4. Show member information
                    5. Show member visits
                    6. Exit program
                    """;
            case ENTER_CHOICE -> "Enter choice: ";
            case ADD_MEMBER -> LS + "MODE: ADD MEMBER, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case MEMBER_NAME -> "Enter name for member: ";
            case MEMBER_SOCIAL -> "Enter social security for member: ";
            case MEMBER_ADDED -> YEL + "Member successfully added" + RESET + LS + LS;
            case PAYMENT_MENU -> LS + "MODE: ADD PAYMENT, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case PAYMENT_ADDED -> YEL + "Payment successfully added" + RESET + LS + LS;
            case SHOW_MEMBER_MENU -> LS + "MODE: SHOW MEMBER, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case NAME_SOCIAL -> "Enter name or social security for member: ";
            case NO_SUCH_MEMBER -> RED + "No such member exists." + RESET + LS;
            case REGISTER_VISIT -> LS + "MODE: REGISTER VISIT, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case INVALID_FORMATTING -> RED + "Social security was not properly formatted" + LS + RESET;
            case VISIT_HISTORY -> LS + "MODE: VISIT HISTORY, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case NOT_ACTIVE -> RED + "Membership is not active" + RESET + LS;
            case VISIT_REGISTERED -> YEL + "Visit successfully registered" + RESET + LS;
            case ALREADY_EXIST -> RED + "Member already in database" + RESET + LS;
            case NO_HISTORY -> RED + "Member has no registered visits" + RESET + LS;
        };
    }

    public static String removeHyphen(String social) {
        if (social.isEmpty()) {
            throw new IllegalArgumentException("Social security can not be empty");
        }
        String[] parts = social.trim().split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Social security was not properly formatted");
        }
        return parts[0] + parts[1];
    }

    public static String addHyphen(long socialSecurity) {
        StringBuilder builder = new StringBuilder();
        String temp = Long.toString(socialSecurity);
        if (socialSecurity < 1000000000) {
            builder.append("0").append(temp, 0, 5).
                    append("-").append(temp,5,9);
        } else {
            builder.append(temp,0,6).
                    append("-").append(temp,6,10);
        }
        return builder.toString();
    }

    public static String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return formatter.format(time);
    }
}
