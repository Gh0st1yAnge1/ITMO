package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.Response;
import org.Gh0st1yAnge1.utils.IdGenerator;

public class Clear implements ClientCommand {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg != null){
            return new Response(false, "Usage: clear", null);
        }

        collectionManager.clear();
        IdGenerator.reset();
        return new Response(true, "Successfully cleared!", null);
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clears collection";
    }
}
