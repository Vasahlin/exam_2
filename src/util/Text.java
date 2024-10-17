package util;

public final class Text {

    public enum message {
        MAIN_MENU, ADD_MEMBER, ENTER_CHOICE,
        MEMBER_NAME, MEMBER_SOCIAL, MEMBER_ADDED,
        PAYMENT_ADDED

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
                    5. Edit member
                    6. Exit program
                    """;
            case ENTER_CHOICE -> "Enter choice: ";
            case ADD_MEMBER -> LS + "MODE: ADD MEMBER, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case MEMBER_NAME -> "Enter name for member: ";
            case MEMBER_SOCIAL -> "Enter social security for member: ";
            case MEMBER_ADDED -> "Member successfully added";
            case PAYMENT_ADDED -> "Payment successfully added";
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
}
