package org.example.command;

import org.example.manager.CollectionManager;

public class AverageOfDistance implements Command{

    private final CollectionManager collectionManager;

    public AverageOfDistance(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 0){
            System.out.println("Usage: average_of_distance");
            return;
        }

        System.out.println("Average distance: " + collectionManager.averageOfDistance());
    }

    @Override
    public String getName() {
        return "average_of_distance";
    }

    @Override
    public String getDescription() {
        return "gives you average of distance fields in collection";
    }
}
