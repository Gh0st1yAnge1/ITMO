package org.example.command;

public class Exit implements Command{

    @Override
    public void execute(String[] args) {

        if (args.length != 0){
            System.out.println("Usage: exit");
            return;
        }

        System.out.println("Program's terminated.");
        System.exit(0);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "terminates program.";
    }
}
