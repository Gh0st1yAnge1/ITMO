package org.example.command;

import org.example.manager.CommandManager;

public class Help implements Command{

    private final CommandManager commandManager;

    public Help(CommandManager commandManager){
        this.commandManager = commandManager;
    }


    @Override
    public void execute(String[] args) {

        if (args.length != 0){
            System.out.println("Usage: help");
            return;
        }

        System.out.println("Available commands:");
        for (Command command: commandManager.getCommands().values()){
            System.out.println(" ");
            System.out.println("--" + command.getName() + "--");
            System.out.println(command.getDescription());
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "shows available commands";
    }
}
