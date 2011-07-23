package org.jruby.extras.demo;

import java.util.Arrays;
import org.jcodings.specific.UTF8Encoding;
import org.jruby.util.ByteList;
import static java.lang.System.*;

public class SimpleByteList {
    public static void main(String[] args) throws Exception {
        String str = "This ïs a s∑mplé ByteList";
        ByteList fromString = new ByteList(str.getBytes("UTF-8"), UTF8Encoding.INSTANCE);
        
        out.println("String: "      + str);
        out.println("Char length: " + str.length());
        out.println("Byte length: " + fromString.getRealSize());
        out.println("Bytes: "       + Arrays.toString(fromString.bytes()));
        out.println("Encoding: "    + fromString.getEncoding());
    }
}
