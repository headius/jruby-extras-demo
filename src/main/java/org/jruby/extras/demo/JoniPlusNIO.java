package org.jruby.extras.demo;

import static java.lang.System.*;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.jcodings.specific.ASCIIEncoding;
import org.joni.Matcher;
import org.joni.Regex;
import org.jruby.util.ByteList;

public class JoniPlusNIO {
    public static void main(String[] args) throws Exception {
        // Open a channel to "words"
        FileInputStream fis = new FileInputStream("/usr/share/dict/words");
        FileChannel fc = fis.getChannel();
        
        // Read all words into a Java byte[]
        byte[] bytes = new byte[(int)fc.size()];
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        fc.read(buf);
        
        // Prepare regex and match against words
        ByteList pattern = ByteList.create("(^.*fish.*$)");
        Regex regex = new Regex(pattern.bytes(), 0, pattern.getRealSize(), 0, ASCIIEncoding.INSTANCE);
        Matcher matcher = regex.matcher(bytes);
        
        // Walk words, tallying up matches
        int start = 0;
        int count = 0;
        while ((start = matcher.search(start, bytes.length, 0)) != -1) {
            count += 1;
            start = matcher.getEnd();
        }
        out.println("Words with 'fish' in them: " + count);
    }
}
