package Validate;

import java.util.regex.Pattern;

public class Validate {
    private static final String SORT_VALIDATE = "^[12]$";

    public static boolean sortValidate(String choice){
        Pattern pattern = Pattern.compile(SORT_VALIDATE);
        boolean isMatch = pattern.matcher(choice).matches();
        return isMatch;
    }

}
