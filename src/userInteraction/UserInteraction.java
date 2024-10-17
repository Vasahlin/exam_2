package userInteraction;

import clients.GymMember;
import clients.Members;
import util.*;
import util.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class UserInteraction {
    private final static Path memberData = Paths.get(java.lang.String.format("%s\\src\\clients\\members.txt",
            System.getProperty("user.dir")));
    private static Members members;

    public UserInteraction() {
        try {
            members = new Members(IO.readFile(memberData));
        } catch (IllegalArgumentException | IOException e) {
            IO.printError(e.getMessage());
        }
    }

    public int start() {
        String menuChoice;
        while (true) {
            try {
                IO.printMessage(Text.message.MAIN_MENU);
                IO.printMessage(Text.message.ENTER_CHOICE);
                if ((menuChoice = IO.readLine()).equals("6")) {
                    break;
                }
                switch (menuChoice) {
                    case "1": break; case "2": addMember();break;
                    case "3": break;
                }
            } catch (IOException e) {
                IO.printError(e.getMessage());
            }

        }
        return 0;
    }

    private void addMember() {
        String name, input;
        long socialSecurity;
        LocalDate timeOfPayment;
        while (true) {
            try {
                IO.printMessage(Text.message.ADD_MEMBER);
                IO.printMessage(Text.message.MEMBER_NAME);
                if (Validate.equalsExit(input = IO.readLine())) {
                    break;
                }
                name = Validate.validateName(input);
                IO.printMessage(Text.message.MEMBER_SOCIAL);
                if (Validate.equalsExit(input = IO.readLine())) {
                    break;
                }
                input = Validate.validateSocialSecurity(input);
                socialSecurity = Long.parseLong(input);
                if (!members.memberExist(socialSecurity)) {
                    timeOfPayment = LocalDate.now();
                    members.addMember(new GymMember(name, socialSecurity, timeOfPayment));
                    IO.printMessage(Text.message.MEMBER_ADDED);
                    break;
                }
            } catch (IllegalArgumentException | IOException e) {
                if (e instanceof IOException) {
                    IO.printError(Text.genericError);
                } else {
                    IO.printError(e.getMessage());
                }
            }
        }
    }

    private void addPayment() {
        String input;
        long socialSecurity;
        while (true) {
            try {
                if (Validate.equalsExit(input = IO.readLine())) {
                    break;
                }
                input = Validate.validateSocialSecurity(input);
                socialSecurity = Long.parseLong(input);
                if (!members.memberExist(socialSecurity)) {

                    IO.printMessage(Text.message.PAYMENT_ADDED);
                    break;
                }
            } catch (IllegalArgumentException | IOException e) {
                if (e instanceof IOException) {
                    IO.printError(Text.genericError);
                } else {
                    IO.printError(e.getMessage());
                }
            }
        }
    }
}
