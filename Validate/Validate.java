package Validate;

import java.util.regex.Pattern;

public class Validate {
    private static final String SORT_VALIDATE = "^[12]$";
    private static final String SIGN_UP_VALIDATE = "^[a-z0-9]{6,}$";

    public static boolean sortValidate(String choice){
        Pattern pattern = Pattern.compile(SORT_VALIDATE);
        boolean isMatch = pattern.matcher(choice).matches();
        return isMatch;
    }
    public static boolean Signup_validate(String valid){
        Pattern pattern = Pattern.compile(SIGN_UP_VALIDATE);
        boolean isMatch = pattern.matcher(valid).matches();
        return isMatch;
    }

}
