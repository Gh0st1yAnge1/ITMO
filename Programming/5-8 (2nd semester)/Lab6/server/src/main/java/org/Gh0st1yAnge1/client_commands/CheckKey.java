package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.Response;

public class CheckKey implements ClientCommand {
    private final CollectionManager collectionManager;

    public CheckKey(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg, Route route) {

        if (arg == null || arg.isBlank()){
            return new Response(false, "Usage: insert <key>", null);
        }

        int key;
        try {
            key = Integer.parseInt(arg);
        } catch (NumberFormatException ex){
            return new Response(false, "Key must be integer.", null);
        }

        if (collectionManager.checkKey(key)){
            return new Response(false, "Key already exists.", null);
        } else {
            return new Response(true, "Key is available!", null);
        }
    }

    @Override
    public String getName() {
        return "check_key";
    }

    @Override
    public String getDescription() {
        return "checks_key";
    }
}
