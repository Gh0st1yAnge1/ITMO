package org.example.command;

import org.example.manager.CollectionManager;
import org.example.model.Route;
import org.example.util.RouteBuilder;

public class ReplaceIfLower implements Command{

    private final CollectionManager collectionManager;
    private final RouteBuilder routeBuilder;

    public ReplaceIfLower(CollectionManager collectionManager, RouteBuilder routeBuilder){
        this.collectionManager = collectionManager;
        this.routeBuilder = routeBuilder;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: replace_if_lower <key>");
            return;
        }

        try{
            Integer id = Integer.parseInt(args[0]);
            Route route = routeBuilder.buildRoute();
            if (collectionManager.replaceIfLower(id, route)){
                System.out.println("Element replaced.");
            } else {
                System.out.println("New element is more than old or they're equals.");
            }
        }catch (NumberFormatException ex){
            System.out.println("Key must be integer.");
        }

    }

    @Override
    public String getName() {
        return "replace_if_lower";
    }

    @Override
    public String getDescription() {
        return "replaces element using a key,\nif new value is less than old";
    }
}
