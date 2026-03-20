package org.example.command;

import org.example.manager.CollectionManager;

public class CountByDistance implements Command{

    private final CollectionManager collectionManager;

    public CountByDistance(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1){
            System.out.println("Usage: count_by_distance <distance>");
            return;
        }

        try{
            double distance = Double.parseDouble(args[0]);
            System.out.println("Answer: " + collectionManager.countByDistance(distance));
        } catch (NumberFormatException ex){
            System.out.println("Distance must be double.");
        }

    }

    @Override
    public String getName() {
        return "count_by_distance";
    }

    @Override
    public String getDescription() {
        return "shows number of elements, which distance fields\nare equals to inserted value";
    }
}
