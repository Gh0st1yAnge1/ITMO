package org.example.command;

import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.util.IdGenerator;

public class Clear implements Command{

    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    public Clear(CollectionManager collectionManager, CommandManager commandManager){
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 0){
            System.out.println("Usage: clear");
            return;
        }

        collectionManager.clear();
        IdGenerator.reset();
        System.out.println("Successfully cleared!");
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clears collection";
    }
}
