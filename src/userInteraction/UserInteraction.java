package userInteraction;

import clients.GymMember;
import clients.Members;
import util.*;
import util.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

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
                if (Validate.equalsExit(menuChoice = IO.readLine())) {
                    return 0;
                }
                switch (menuChoice) {
                    case "1":               break; case "2": addMember();       break;
                    case "3": addPayment(); break; case "4": showMemberInfo();  break;
                }
            } catch (IOException e) {
                IO.printError(e.getMessage());
            }
        }
    }

    private void registerVisit() {

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
        int memberIndex;
        while (true) {
            try {
                IO.printMessage(Text.message.PAYMENT_MENU);
                IO.printMessage(Text.message.MEMBER_SOCIAL);
                if (Validate.equalsExit(input = IO.readLine())) {
                    break;
                }
                input = Validate.validateSocialSecurity(input);
                socialSecurity = Long.parseLong(input);
                memberIndex = members.getMemberIndex(socialSecurity);
                if (memberIndex >= 0) {
                    members.getMember(memberIndex).addPayment(LocalDate.now());
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

    private void showMemberInfo() {
        String input;
        ArrayList<GymMember> sharedNames;
        long socialSecurity;
        int index;

        while(true) {
            try {
                IO.printMessage(Text.message.SHOW_MEMBER_MENU);
                IO.printMessage(Text.message.NAME_SOCIAL);
                if (Validate.equalsExit(input = IO.readLine())) {
                    break;
                }
                if (Validate.ableToParseSocial(input) &&
                    members.memberExist(socialSecurity = parseLong(input))) {
                    index = members.getMemberIndex(socialSecurity);
                    System.out.println(members.getMember(index).toString());
                }
                else if (!(sharedNames = members.getMembersByName(input)).isEmpty()) {
                    for (GymMember m : sharedNames) {
                        System.out.println(m.toString());
                   }
                } else {
                    IO.printMessage(Text.message.NO_SUCH_MEMBER);
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

    private long parseLong(String input) {
        String temp = Text.removeHyphen(input);
        return Long.parseLong(temp);
    }
}
