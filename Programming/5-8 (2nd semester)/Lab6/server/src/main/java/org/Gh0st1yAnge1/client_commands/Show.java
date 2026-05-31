package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.Response;

public class Show implements ClientCommand {

    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg != null){
            return new Response(false, "Usage: show", null);
        }

        return new Response(true, "All available routes:", collectionManager.showCollection());
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
