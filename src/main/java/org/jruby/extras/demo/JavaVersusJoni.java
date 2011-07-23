package org.jruby.extras.demo;

import java.util.regex.Pattern;

public class JavaVersusJoni {
    public static void main(String[] args) {
        Pattern regex = Pattern.compile("x{1,5}");
        StringBuffer longBuf = new StringBuffer();
        for (int i=0;i<100000;i++) {
            longBuf.append("xxxxxxxxxxxxxxxxxx");
        }

        
        regex.matcher(longBuf).find();
    }
}
