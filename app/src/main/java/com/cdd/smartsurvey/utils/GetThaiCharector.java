package com.cdd.smartsurvey.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class GetThaiCharector {

    public static Boolean checkThai(CharSequence arg) throws UnsupportedEncodingException {
        Boolean returnFont;
        Pattern mPattern = Pattern.compile("^[ๅภถุึคตจขชๆไำพะัีรนยบลฃฟหกดเ้่าสวงผปแอิืทมใฝ๔ูฎฑธํ๊ณฯญฐฅฤฆฏโฌ็๋ษศซฉฮฺ์ฒฬฦ\\s]+$");
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

    public static Boolean checkThaiNumber(CharSequence arg) throws UnsupportedEncodingException {
        Boolean returnFont;
        Pattern mPattern = Pattern.compile("^[ๅภถุึคตจขชๆไำพะัีรนยบลฃฟหกดเ้่าสวงผปแอิืทมใฝ๑๒๓๔ูฎฑธํ๊ณฯญฐฅฤฆฏโฌ็๋ษศซฉฮฺ์ฒฬฦ1-9!-/:-@\\[-`{-~\\s]+$");
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
