package org.example.command;

import org.example.manager.CollectionManager;

public class Info implements Command{
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args)
    {
        if (args.length != 0){
            System.out.println("Usage: info");
            return;
        }
        collectionManager.info();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "shows info about collection";
    }
}
