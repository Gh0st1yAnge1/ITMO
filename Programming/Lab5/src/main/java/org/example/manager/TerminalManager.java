package org.example.manager;

public class TerminalManager {
    public static void enableRawMode(){
        try{
            Runtime.getRuntime()
                    .exec(new String[]{"sh","-c","stty -icanon -echo < /dev/tty"})
                    .waitFor();
        } catch (Exception e){
            System.out.println("Cannot enable raw mode");
        }
    }

    public static void disableRawMode(){
        try{
            Runtime.getRuntime()
                    .exec(new String[]{"sh","-c","stty sane < /dev/tty"})
                    .waitFor();
        } catch (Exception e){
            System.out.println("Cannot restore terminal.");
        }
    }
}
