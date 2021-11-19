package javaWrapper;

public class JavaWrapper {
    public static void main(String[] args) {
        try{
            CommandLineRunner cr = new CommandLineRunner(args);
            WekaRunner runner = new WekaRunner(cr.dataFile);
            runner.Start();
        } catch(IllegalStateException ex) {
            ex.printStackTrace();
        }
    }
}
