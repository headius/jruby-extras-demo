package org.jruby.extras.demo;

import org.jruby.util.ByteList;
import static java.lang.System.*;

public class ByteListOperations {
    public static void main(String[] args) {
        ByteList simple = ByteList.create("The quick brown");
        out.println(simple);
        
        simple.append((byte)' ');
        simple.append((byte)'f');
        simple.append('o');
        simple.append('x');
        out.println(simple);
        
        simple.append(new byte[] {32, 106, 117, 109, 112, 101, 100});
        out.println(simple);
        
        out.println(simple.endsWith(ByteList.create("jumped")));
        
        simple.delete(4, 6); // remove subset
        out.println(simple);
        
        simple.view(4, 10); // narrow view to subset
        out.println(simple);
    }
}
