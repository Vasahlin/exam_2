package util;

public final class Text {

    public enum message {
        MAIN_MENU, ADD_MEMBER, ENTER_CHOICE,
        MEMBER_NAME, MEMBER_SOCIAL, MEMBER_ADDED,
        PAYMENT_MENU, PAYMENT_ADDED, SHOW_MEMBER_MENU,
        NAME_SOCIAL, NO_SUCH_MEMBER

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
                    5. Exit program
                    """;
            case ENTER_CHOICE -> "Enter choice: ";
            case ADD_MEMBER -> LS + "MODE: ADD MEMBER, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case MEMBER_NAME -> "Enter name for member: ";
            case MEMBER_SOCIAL -> "Enter social security for member: ";
            case MEMBER_ADDED -> "Member successfully added";
            case PAYMENT_MENU -> LS + "MODE: ADD PAYMENT, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case PAYMENT_ADDED -> "Payment successfully added";
            case SHOW_MEMBER_MENU -> LS + "MODE: SHOW MEMBER, enter " + YEL + "Exit" + RESET + " to escape" + LS;
            case NAME_SOCIAL -> "Enter name or social security for member: ";
            case NO_SUCH_MEMBER -> "No such member exists." + LS;
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
}
