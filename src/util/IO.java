package util;

import clients.GymMember;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public final class IO {
    final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static ArrayList<GymMember> readFile(Path filePath) throws IOException, IllegalArgumentException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            ArrayList<GymMember> members = new ArrayList<>();
            String temp;
            while ((temp = br.readLine()) != null) {
                String[] parts = temp.split(", ");
                if ((temp = br.readLine()) == null) {
                    throw new IllegalArgumentException("Missing information concerning payment in text file");
                }
                LocalDate lastPayment = LocalDate.parse(temp);
                if (Validate.validSocial(parts[0]) && Validate.validName(parts[1])) {
                    members.add(new GymMember(parts[1], Long.parseLong(parts[0]), lastPayment));
                } else {
                    throw new IllegalArgumentException("Data in text file does not match expected type.");
                }
            }
            return members;
        }
    }

    public static String readLine() throws IOException {
        return reader.readLine().trim();
    }

    public static void printMessage(Text.message instruction) {
        switch (instruction) {
            case MAIN_MENU: System.out.println(
                    Text.generateMessage(Text.message.MAIN_MENU));      break;
            case ENTER_CHOICE: System.out.print(
                    Text.generateMessage(Text.message.ENTER_CHOICE));   break;
            case ADD_MEMBER: System.out.print(
                    Text.generateMessage(Text.message.ADD_MEMBER));     break;
            case MEMBER_NAME: System.out.print(
                    Text.generateMessage(Text.message.MEMBER_NAME));    break;
            case MEMBER_SOCIAL: System.out.print(
                    Text.generateMessage(Text.message.MEMBER_SOCIAL));  break;
            case MEMBER_ADDED: System.out.print(
                    Text.generateMessage(Text.message.MEMBER_ADDED));   break;
            case PAYMENT_MENU: System.out.print(
                    Text.generateMessage(Text.message.PAYMENT_MENU));   break;
            case PAYMENT_ADDED: System.out.print(
                    Text.generateMessage(Text.message.PAYMENT_ADDED));  break;
            case SHOW_MEMBER_MENU: System.out.print(
                    Text.generateMessage(Text.message.SHOW_MEMBER_MENU));break;
            case NAME_SOCIAL: System.out.print(
                    Text.generateMessage(Text.message.NAME_SOCIAL));    break;
            case NO_SUCH_MEMBER: System.out.print(
                    Text.generateMessage(Text.message.NO_SUCH_MEMBER)); break;
            case REGISTER_VISIT: System.out.print(
                    Text.generateMessage(Text.message.REGISTER_VISIT)); break;
            case INVALID_FORMATTING: System.out.print(
                    Text.generateMessage(Text.message.INVALID_FORMATTING)); break;
            case VISIT_HISTORY: System.out.print(
                    Text.generateMessage(Text.message.VISIT_HISTORY)); break;
            case NOT_ACTIVE: System.out.print(
                    Text.generateMessage(Text.message.NOT_ACTIVE)); break;
            case VISIT_REGISTERED: System.out.print(
                    Text.generateMessage(Text.message.VISIT_REGISTERED)); break;
            case ALREADY_EXIST: System.out.print(
                    Text.generateMessage(Text.message.ALREADY_EXIST)); break;
            case NO_HISTORY: System.out.print(
                    Text.generateMessage(Text.message.NO_HISTORY)); break;
            default: break;
        }
    }

    public static void printError(String errorMessage) {
        System.out.println(Text.RED + errorMessage + Text.RESET);
    }
}