package org.example.command;

import org.example.manager.CollectionManager;
import org.example.model.Route;
import org.example.util.RouteBuilder;

public class Update implements Command{

    private final CollectionManager collectionManager;
    private final RouteBuilder routeBuilder;

    public Update(CollectionManager collectionManager, RouteBuilder routeBuilder){
        this.collectionManager = collectionManager;
        this.routeBuilder = routeBuilder;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1){
            System.out.println("Usage: update <key>");
            return;
        }

        try{
            Integer id = Integer.parseInt(args[0]);
            Route route = routeBuilder.buildRoute();
            if (collectionManager.update(id, route)){
                System.out.println("Element's updated!");
            } else {
                System.out.println("Key doesn't exists.");
            }
        } catch (NumberFormatException ex){
            System.out.println("Key must be integer.");
        }

    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "updates element using a key";
    }
}
