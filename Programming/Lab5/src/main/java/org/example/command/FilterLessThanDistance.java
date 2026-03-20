package org.example.command;

import org.example.manager.CollectionManager;
import org.example.model.Route;

public class FilterLessThanDistance implements Command{

    private final CollectionManager collectionManager;

    public FilterLessThanDistance(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1){
            System.out.println("Usage: filter_less_than_distance <distance>");
            return;
        }

        try{
            double distance = Double.parseDouble(args[0]);
            for (Route route: collectionManager.filterLessThanDistance(distance)){
                System.out.println(route);
            }
        } catch (NumberFormatException ex){
            System.out.println("Distance must be double");
        }

    }

    @Override
    public String getName() {
        return "filter_less_than_distance";
    }

    @Override
    public String getDescription() {
        return "shows elements, which distance field\nis less than inserted value";
    }
}
