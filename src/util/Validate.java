package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class Validate {

    public static String validateName(String name) throws IllegalArgumentException{
        if (!validName(name)) {
            throw new IllegalArgumentException("Name must at least include two characters");
        }
        return name;
    }

    public static boolean equalsExit(String toExamine) {
        return toExamine.equalsIgnoreCase("exit") || toExamine.equals("6");
    }

    public static boolean validName(String name) {
        int count = 0;
        for (char c : name.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                count++;
            }
        }
        return (count > 2);
    }

    public static boolean withinOpeningHours(LocalDateTime toInspect) {
        LocalTime opening = LocalTime.of(10,0);
        LocalTime close = LocalTime.of(22,0);
        LocalTime time = toInspect.toLocalTime();

        return time.isAfter(opening) && time.isBefore(close);
    }

    public static boolean ableToParseSocial(String social) {
        try {
            validateSocialSecurity(social);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

   public static String validateSocialSecurity(String social) throws IllegalArgumentException {
       social = Text.removeHyphen(social);
       if (!validSocial(social)) {
           throw new IllegalArgumentException("Social security was not properly formatted");
       }
       return social;
   }

    public static boolean validSocial(String social) throws IllegalArgumentException {
        if (notValidFormat(social)) {
            return false;
        }
        return !notTenDigits(social);
    }

    public static boolean notTenDigits(String socialSecurity) throws IllegalArgumentException  {
        int count = 0;
        for (char c : socialSecurity.toCharArray()) {
            if(Character.isDigit(c)) {
                count++;
            } else {
                throw new IllegalArgumentException("Social Security must only contain digits");
            }
        }
        return count != 10;
    }

    public static boolean notValidFormat(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDate.parse(date.substring(0,6), formatter);
            return false;
        } catch (DateTimeParseException e){
            return true;
        }
    }
}
