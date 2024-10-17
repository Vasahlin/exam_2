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
        return reader.readLine();
    }

    public static void printMessage(Text.message instruction) {
        switch (instruction) {
            case MAIN_MENU: System.out.print(
                    Text.generateMessage(Text.message.MAIN_MENU));break;
            case ENTER_CHOICE: System.out.print(
                    Text.generateMessage(Text.message.ENTER_CHOICE));break;
            case ADD_MEMBER: System.out.print(
                    Text.generateMessage(Text.message.ADD_MEMBER));break;
            case MEMBER_NAME: System.out.print(
                    Text.generateMessage(Text.message.MEMBER_NAME));break;
            case MEMBER_SOCIAL: System.out.print(
                    Text.generateMessage(Text.message.MEMBER_SOCIAL));break;
            default: break;
        }
    }

    public static void printError(String errorMessage) {
        System.out.println(Text.RED + errorMessage + Text.RESET);
    }
}