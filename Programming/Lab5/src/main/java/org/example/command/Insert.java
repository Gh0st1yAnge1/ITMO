package org.example.command;

import org.example.exception.InputCancelledException;
import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.model.Route;
import org.example.util.IdGenerator;
import org.example.util.RouteBuilder;

public class Insert implements Command{
    private final CollectionManager collectionManager;
    private final RouteBuilder routeBuilder;
    private final CommandManager commandManager;

    public Insert(CollectionManager collectionManager,
                  RouteBuilder routeBuilder, CommandManager commandManager){
        this.collectionManager = collectionManager;
        this.routeBuilder = routeBuilder;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1){
            System.out.println("Usage: insert <key>");
            return;
        }

        try {
            Integer key = Integer.parseInt(args[0]);
            if (collectionManager.checkKey(key)){
                System.out.println("Key already exists.");
                return;
            }

            Route route = routeBuilder.buildRoute();
            collectionManager.insert(key, route);
            System.out.println("Element inserted.");

        } catch (NumberFormatException ex){
            System.out.println("Key must be integer.");
        }
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "adds new element using a key";
    }
}
