package org.example.command;

import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.model.Route;
import org.example.util.RouteBuilder;

public class RemoveGreater implements Command{

    private final CollectionManager collectionManager;
    private final RouteBuilder routeBuilder;
    private final CommandManager commandManager;

    public RemoveGreater(CollectionManager collectionManager, RouteBuilder routeBuilder, CommandManager commandManager){
        this.collectionManager = collectionManager;
        this.routeBuilder = routeBuilder;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 0){
            System.out.println("Usage: remove_greater");
            return;
        }

        Route route = routeBuilder.buildRoute();
        int result = collectionManager.removeGreater(route);

        if(result == 0){
            System.out.println("Collection size didn't change.");
        } else {
            System.out.println("Successfully removed!");
            System.out.println("Number of removed elements: " + result);
            commandManager.execute("save");
        }
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "removes all collection elements,\nwho is more than inserted";
    }
}
