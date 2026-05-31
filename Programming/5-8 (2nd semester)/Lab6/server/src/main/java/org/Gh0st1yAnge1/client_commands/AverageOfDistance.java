package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.Response;

public class AverageOfDistance implements ClientCommand {

    private final CollectionManager collectionManager;

    public AverageOfDistance(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg != null){
            return new Response(false, "Usage: average_of_distance", null);
        }

        double avgDist = collectionManager.averageOfDistance();

        return new Response(true, "Average distance: " + avgDist, null);
    }

    public String getName() {
        return "average_of_distance";
    }

    @Override
    public String getDescription() {
        return "gives you average of distance fields in collection";
    }
}
