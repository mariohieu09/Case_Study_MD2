package Controller;

import java.util.regex.Pattern;

public class ValidateChoice {
    private static final String MENU_CHOICE = "^[1230]$";
    private static final String CHOICE = "^[12340]$";
    private static final String SORT_CHOICE = "^[123]$";
    private static final String CONFIRM_CHOICE = "^[12]$";

    public static boolean menuChoice(String choice){
        Pattern pattern = Pattern.compile(MENU_CHOICE);
        boolean isMatch = pattern.matcher(choice).matches();
        return isMatch;
    }

    public static boolean Choice(String choice){
        Pattern pattern = Pattern.compile(CHOICE);
        boolean isMatch = pattern.matcher(choice).matches();
        return isMatch;
    }
    public static boolean Sort_Choice(String choice){
        Pattern pattern = Pattern.compile(SORT_CHOICE);
        boolean isMatch = pattern.matcher(choice).matches();
        return isMatch;
    }
    public static boolean confirmValidate(String choice){
        Pattern pattern = Pattern.compile(CONFIRM_CHOICE);
        boolean isMatch = pattern.matcher(choice).matches();
        return isMatch;
    }
}
