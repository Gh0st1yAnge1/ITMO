package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.Response;

public class CountByDistance implements ClientCommand {

    private final CollectionManager collectionManager;

    public CountByDistance(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg == null || arg.isBlank()){
            return new Response(false, "Usage: count_by_distance <distance>", null);
        }

        double distance;
        long count;

        try{
            distance = Double.parseDouble(arg);
            count =  collectionManager.countByDistance(distance);
        } catch (NumberFormatException ex){
            return new Response(false, "Usage: count_by_distance <distance>", null);
        }

        return new Response(true, "There are " + count + " objects with same distance", null);
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
