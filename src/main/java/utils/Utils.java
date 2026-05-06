package utils;

import java.util.UUID;

public class Utils {

    public static boolean isValidUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.isBlank())
            return false;
        try{
            UUID.fromString(uuidStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidInteger(String number) {
        if (number == null || number.isBlank())
            return false;
        try{
            Integer.parseInt(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
