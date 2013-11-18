package com.example.gathering.object;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {
	public boolean isValidEmail(String email)
	{
		boolean isValidEmail = false;

	    String emailExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches())
	    {
	    	isValidEmail = true;
	    }
	    return isValidEmail;
	}
	
	public boolean isEmail(String email) {
        Pattern pat = null;
        Matcher mat = null;        
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(email);
        if (mat.find()) {
            System.out.println("[" + mat.group() + "]");
            return true;
        }else{
            return false;
        }        
    }


	public String validName(String name)
	{
		name = name.replace(' ', '_');
		return name;
	}
}
