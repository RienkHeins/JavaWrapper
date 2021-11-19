package javaWrapper;

import org.apache.commons.cli.*;

import java.io.PrintWriter;

public class CommandLineRunner {
    //* Handles command line arguments and checks for errors

    private Options options;
    private final String[] clArguments;
    private CommandLine commandLine;
    public String dataFile;

    private static final Option fileName = new Option("f", "FILE", true, "Name of the file to classify");
    private static final Option helpOption = new Option("h", "HELP", false, "Prints this message");

    public CommandLineRunner(final String[] args){
        // Runs the object functions
        this.clArguments = args;
        buildOptions();
        processCommandLine();
    }

    private void processCommandLine(){
        // Uses the commandline arguments to complete their actions
        try {
            CommandLineParser parser = new DefaultParser();
            this.commandLine = parser.parse(this.options, this.clArguments);
            if (commandLine.hasOption(helpOption.getLongOpt())){
                printHelp(options);
                System.exit(0);
            } else if(commandLine.hasOption(fileName.getLongOpt())){
                dataFile = commandLine.getOptionValue(fileName.getLongOpt());
            } else{
                System.out.println("Wrong command or no command at all was given, please use as followed:\n");
                printHelp(options);
                System.exit(0);
            }
        } catch (ParseException ex) {
            printHelp(options);
            throw new IllegalArgumentException(ex);
        }
    }

    private void buildOptions(){
        // Builds the commmandline options
        this.options = new Options();
        options.addOption(fileName);
        options.addOption(helpOption);
    }

    public static void printHelp(Options options){
        // Prints a usage menu for the user if needed
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter pw = new PrintWriter(System.out);
        pw.println("JavaWrapper " + Math.class.getPackage().getImplementationVersion());
        pw.println();
        formatter.printUsage(pw, 100, "java -jar JavaWrapper -f [filename or path]");
        formatter.printOptions(pw, 100, options, 2, 5);
        pw.close();
    }



}
