package org.jruby.extras.demo;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static java.lang.System.*;
import java.util.logging.Logger;
import jnr.posix.FileStat;
import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;
import jnr.posix.POSIXHandler;

public class PosixDemo {
    public static void main(String[] args) throws Throwable {
        POSIX posix = POSIXFactory.getPOSIX(new MyPosixHandler(), true);
        
        // prepare a file
        FileOutputStream fos = new FileOutputStream("/tmp/dummy.txt");
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeUTF("This is the dummy file\n");
        
        // chmod
        posix.chmod("/tmp/dummy.txt", Integer.parseInt("700", 8));
        
        // stat
        FileStat stat = posix.stat("/tmp/dummy.txt");
        out.println("State for /tmp/dummy.txt");
        out.println("Access time: " + stat.atime());
        out.println("File type: " + stat.ftype());
        out.println("Device: " + stat.dev());
        out.println("Created: " + stat.ctime());
        out.println("Symlink: " + stat.isSymlink());
        out.println("INode: " + stat.ino());
        
        // create symlink
        posix.link("/tmp/dummy.txt", "/tmp/dummy-link.txt");
        
        // read and modify environment
        out.println("JAVA_HOME: " + posix.getenv("JAVA_HOME"));
        posix.setenv("FOOBAR", "hello", 1);
        out.println("FOOBAR: " + posix.getenv("FOOBAR"));
        
        // True exec!
        out.println("Going to exec...next output is from /bin/sh");
        posix.execv("/bin/sh", new String[] {"/bin/sh", "-c",  "echo $FOOBAR"});
        
        // Never reached
        out.println("We didn't really exec!");
    }
    
    private static class MyPosixHandler implements POSIXHandler {
        private static final Logger LOG = Logger.getLogger("MyPosixHandler");
        
        public void error(com.kenai.constantine.platform.Errno errno, String string) {
            LOG.warning("POSIX call triggered an error: " + errno);
        }

        public void unimplementedError(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void warn(WARNING_ID wrngd, String string, Object... os) {
            LOG.warning("POSIX call triggered a warning: " + wrngd);
        }

        public boolean isVerbose() {
            return false;
        }

        public File getCurrentWorkingDirectory() {
            return new File(System.getProperty("user.dir"));
        }

        public String[] getEnv() {
            return new String[0];
        }

        public InputStream getInputStream() {
            return System.in;
        }

        public PrintStream getOutputStream() {
            return System.out;
        }

        public int getPID() {
            return -1;
        }

        public PrintStream getErrorStream() {
            return System.err;
        }
        
    }
}
