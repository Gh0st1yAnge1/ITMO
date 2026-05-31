package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.ServerCommandExecutor;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;

public class Update implements ClientCommand {

    private final CollectionManager collectionManager;
    private final ServerCommandExecutor serverCommandExecutor;

    public Update(CollectionManager collectionManager, ServerCommandExecutor serverCommandExecutor){
        this.collectionManager = collectionManager;
        this.serverCommandExecutor = serverCommandExecutor;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg == null || arg.isBlank()){
            return new Response(false,"Usage: update <key>", null);
        }

        if (route == null){
            return new Response(false, "Route must not be null", null);
        }

        int id;
        try{
            id = Integer.parseInt(arg);
        } catch (NumberFormatException ex){
            return new Response(false, "Key must be integer.", null);
        }

        if (collectionManager.update(id, route)){
            serverCommandExecutor.execute(new Request(CommandType.SAVE_SERVER, null, null));
            return new Response(true, "Element updated!", null);
        } else {
            return new Response(false, "Key does not exists", null);
        }

    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "updates element using a key";
    }
}
