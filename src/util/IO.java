package util;

import clients.GymMember;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public final class IO {
    public static ArrayList<GymMember> readFile(Path filePath) throws IOException, IllegalArgumentException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            ArrayList<GymMember> members = new ArrayList<>();
            String temp;
            while ((temp = br.readLine()) != null) {
                String[] parts = temp.split(", ");
                temp = br.readLine();
                LocalDate lastPayment = LocalDate.parse(temp);
                if (Validate.validSocialSecurity(parts[0]) && Validate.validName(parts[1])) {
                    members.add(new GymMember(parts[1], Long.parseLong(parts[0]), lastPayment));
                } else {
                    throw new IllegalArgumentException("Data in text file does not match expected type.");
                }
            }
            return members;
        }
    }
}


