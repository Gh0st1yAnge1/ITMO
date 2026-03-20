package org.example.command;

import org.example.manager.CollectionManager;
import org.example.model.Route;

public class Show implements Command{

    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 0){
            System.out.println("Usage: show");
            return;
        }

        for (Route route: collectionManager.show().values()){
            System.out.println(route);
        }
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "shows all collection elements";
    }
}
