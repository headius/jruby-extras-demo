package org.jruby.extras.demo;

import org.joni.Matcher;
import org.joni.Regex;
import org.joni.Region;
import org.jruby.util.ByteList;
import static java.lang.System.*;

public class SimpleJoni {
    public static void main(String[] args) {
        ByteList string = ByteList.create("The quick brown fox jumped over the lazy dog.");
        String pattern = "(?<a>\\S+) fox (?<b>\\S+)";
        Regex regex = new Regex("(?<a>\\S+) fox (?<b>\\S+)");
        
        // Also can feed bytes directly in
        // Regex regex = new Regex(pattern.getBytes(), 0, pattern.length(), 0, USASCIIEncoding.INSTANCE);
        
        Matcher matcher = regex.matcher(string.bytes());
        
        out.println("String: " + string);
        out.println("Regex: " + pattern);
        out.println("Match at: " + matcher.search(0, string.getRealSize(), 0));
        
        Region reg = matcher.getRegion();
        int backref = regex.nameToBackrefNumber("a".getBytes(), 0, 1, reg);
        out.println("Group 'a': " + string.makeShared(reg.beg[backref], reg.end[backref] - reg.beg[backref]));
        
        backref = regex.nameToBackrefNumber("b".getBytes(), 0, 1, reg);
        out.println("Group 'b': " + string.makeShared(reg.beg[backref], reg.end[backref] - reg.beg[backref]));
    }
}
