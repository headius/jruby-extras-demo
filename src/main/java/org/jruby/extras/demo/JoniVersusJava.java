package org.jruby.extras.demo;

import java.util.regex.Pattern;
import org.jcodings.specific.USASCIIEncoding;
import org.joni.Matcher;
import org.joni.Regex;
import org.jruby.util.ByteList;

public class JoniVersusJava {
    public static void main( String[] args ) {
        for (int i = 0; i < 10; i++) {
            benchJoni();
            benchJava();
        }
    }
    
    private static void benchJoni() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("hello, world");
        ByteList bl = ByteList.create(buffer.toString());
        ByteList pattern = ByteList.create("ello");
        Regex r = new Regex(pattern.bytes(), 0, pattern.getRealSize(), 0, USASCIIEncoding.INSTANCE);
        Matcher m = r.matcher(bl.bytes());
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            int beg = 0;
            while ((beg = m.search(beg, bl.getRealSize(), 0)) != -1) {
                beg += 1;
            }
        }
        System.out.println("joni: " + (System.currentTimeMillis() - start));
    }
    
    private static void benchJava() {
        Pattern p = Pattern.compile("ello");
        java.util.regex.Matcher m2 = p.matcher("hello, world");
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            int beg = 0;
            while (m2.find(beg)) {
                beg = m2.start() + 1;
            }
        }
        System.out.println("java: " + (System.currentTimeMillis() - start));
    }
}
