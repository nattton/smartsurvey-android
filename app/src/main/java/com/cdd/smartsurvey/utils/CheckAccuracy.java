package com.cdd.smartsurvey.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class CheckAccuracy {

    private static Boolean CalCheckSumIDCard(CharSequence arg) throws UnsupportedEncodingException {
        Boolean returnCal = false;
        int calvalue = 0;
        int modvalue = 0;
        int beforecheckdigit = 0;

        calvalue = (Integer.parseInt(arg.toString().substring(0,1))  * 13) +
                   (Integer.parseInt(arg.toString().substring(1,2))  * 12) +
                   (Integer.parseInt(arg.toString().substring(2,3))  * 11) +
                   (Integer.parseInt(arg.toString().substring(3,4))  * 10) +
                   (Integer.parseInt(arg.toString().substring(4,5))  * 9) +
                   (Integer.parseInt(arg.toString().substring(5,6))  * 8) +
                   (Integer.parseInt(arg.toString().substring(6,7))  * 7) +
                   (Integer.parseInt(arg.toString().substring(7,8))  * 6) +
                   (Integer.parseInt(arg.toString().substring(8,9))  * 5) +
                   (Integer.parseInt(arg.toString().substring(9,10))  * 4) +
                   (Integer.parseInt(arg.toString().substring(10,11)) * 3) +
                   (Integer.parseInt(arg.toString().substring(11,12)) * 2);

        modvalue = calvalue % 11;
        beforecheckdigit = 11 - modvalue;

        if (String.valueOf(beforecheckdigit).substring(String.valueOf(beforecheckdigit).length() - 1,
                    String.valueOf(beforecheckdigit).length()).equals(arg.toString().substring(12, 13)))
        {
            returnCal = true;
        }
        else
        {
            returnCal = false;
        }

        return returnCal;
    }

    public static Boolean CheckIDCard(CharSequence arg) throws UnsupportedEncodingException {
        Boolean returnFont;
        Pattern mPattern = Pattern.compile("^[0-9]+$");
        if ((mPattern.matcher(arg).matches()) && (Integer.parseInt(arg.toString().substring(0,1)) > 0))
        {
            if (CalCheckSumIDCard(arg) == true) {
                returnFont = true;
            }else{
                returnFont = false;
            }
        }
        else
        {
            returnFont = false;
        }
        if (arg.toString().isEmpty()) { returnFont = true;}
        return returnFont;
    }

    public static Boolean CheckIDHomeNo(CharSequence arg) throws UnsupportedEncodingException {
        Boolean returnFont;
        Pattern mPattern = Pattern.compile("^[1-9]+$");
        if (mPattern.matcher(arg).matches())
        {
            returnFont = true;
        }
        else
        {
            returnFont = false;
        }
        if (arg.toString().isEmpty()) { returnFont = true;}
        return returnFont;
    }
}
