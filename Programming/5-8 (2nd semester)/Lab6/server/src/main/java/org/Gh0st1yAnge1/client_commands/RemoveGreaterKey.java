package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.ServerCommandExecutor;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;

public class RemoveGreaterKey implements ClientCommand {

    private final ServerCommandExecutor serverCommandExecutor;
    private final CollectionManager collectionManager;

    public RemoveGreaterKey(CollectionManager collectionManager, ServerCommandExecutor serverCommandExecutor){
        this.collectionManager = collectionManager;
        this.serverCommandExecutor = serverCommandExecutor;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg == null || arg.isBlank()){
            return new Response(false, "Usage: remove_greater_key <key>", null);
        }

        int id;
        try{
            id = Integer.parseInt(arg);
        } catch (NumberFormatException ex){
            return new Response(false, "Key must be integer.", null);
        }

        int result = collectionManager.removeGreaterKey(id);

        if(result == 0){
            return new Response(true, "Collection size didn't change.", null);
        }

        serverCommandExecutor.execute(new Request(CommandType.SAVE_SERVER, null, null));
        return new Response(true, "Successfully removed!" + "Number of removed elements: " + result, null);
    }

    @Override
    public String getName() {
        return "remove_greater_key";
    }

    @Override
    public String getDescription() {
        return "removes all collection elements,\nwhich key is more than inserted value";
    }
}
