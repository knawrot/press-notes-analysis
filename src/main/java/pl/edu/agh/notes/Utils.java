package pl.edu.agh.notes;

/**
 * Created by Michał Adamczyk.
 */
public class Utils {
    public static boolean isEmptyString(String string){
        return string == null || "".equals(string);
    }

    public static boolean booleanFromString(String string){
        return "1".equals(string);
    }
}
