package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.ServerCommandExecutor;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;

public class ReplaceIfLower implements ClientCommand {

    private final CollectionManager collectionManager;
    private final ServerCommandExecutor serverCommandExecutor;

    public ReplaceIfLower(CollectionManager collectionManager, ServerCommandExecutor serverCommandExecutor){
        this.collectionManager = collectionManager;
        this.serverCommandExecutor = serverCommandExecutor;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg == null || arg .isBlank()) {
            return new Response(false, "Usage: replace_if_lower <key>", null);
        }

        if (route == null){
            return new Response(false, "Route must not be null", null);
        }

        int id;
        try{
            id = Integer.parseInt(arg);
        }catch (NumberFormatException ex){
           return new Response(false, "Key must be integer.", null);
        }

        if (collectionManager.replaceIfLower(id, route)){
            serverCommandExecutor.execute(new Request(CommandType.SAVE_SERVER, null, null));
            return  new Response(true, "Element removed!", null);
        } else {
            return  new Response(true, "New element is more than old or they're equals.", null);
        }

    }

    @Override
    public String getName() {
        return "replace_if_lower";
    }

    @Override
    public String getDescription() {
        return "replaces element using a key,\nif new value is less than old";
    }
}
