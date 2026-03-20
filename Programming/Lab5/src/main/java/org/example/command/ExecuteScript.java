package org.example.command;

import org.example.manager.CommandManager;

public class ExecuteScript implements Command{

    private final CommandManager commandManager;

    public ExecuteScript(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1){
            System.out.println("Usage: execute_script <file_name>");
            return;
        }
        String fileName = args[0];
        commandManager.executeScript(fileName);
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "executes script";
    }
}
