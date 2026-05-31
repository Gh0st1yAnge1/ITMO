package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.ServerCommandExecutor;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;

public class Insert implements ClientCommand {
    private final CollectionManager collectionManager;
    private final ServerCommandExecutor serverCommandExecutor;

    public Insert(CollectionManager collectionManager, ServerCommandExecutor serverCommandExecutor) {
        this.collectionManager = collectionManager;
        this.serverCommandExecutor = serverCommandExecutor;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (route == null){
            return new Response(false, "Route must not be null", null);
        }

        collectionManager.insert(Integer.parseInt(arg), route);
        serverCommandExecutor.execute(new Request(CommandType.SAVE_SERVER, null, null));
        return new Response(true, "Element inserted", null);
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "adds new element using a key";
    }
}
