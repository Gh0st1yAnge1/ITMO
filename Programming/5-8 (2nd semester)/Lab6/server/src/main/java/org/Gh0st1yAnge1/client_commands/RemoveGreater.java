package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.ServerCommandExecutor;
import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;

public class RemoveGreater implements ClientCommand {

    private final CollectionManager collectionManager;
    private final ServerCommandExecutor serverCommandExecutor;

    public RemoveGreater(CollectionManager collectionManager, ServerCommandExecutor serverCommandExecutor){
        this.collectionManager = collectionManager;
        this.serverCommandExecutor = serverCommandExecutor;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg != null){
            return new Response(false, "Usage: remove_greater", null);
        }

        if (route == null){
            return new Response(false, "Route must not be null", null);
        }

        int result = collectionManager.removeGreater(route);

        if(result == 0){
            return new Response(true, "Collection size didn't change.", null);
        }

        serverCommandExecutor.execute(new Request(CommandType.SAVE_SERVER, null, null));
        return  new Response(true, "Successfully removed!" + "Number of removed elements: " + result, null);
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "removes all collection elements,\nwho is more than inserted";
    }
}
