package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.FileManager;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.Response;

import java.util.LinkedHashMap;

public class SaveServer implements ClientCommand {

    private final FileManager fileManager;
    private final CollectionManager collectionManager;

    public SaveServer(FileManager fileManager, CollectionManager collectionManager){
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg, Route route) {

        String answer = fileManager.saveCollection(new LinkedHashMap<>(collectionManager.show()));
        if (answer.equals("File path is not found.")){
            return new Response(false, "File path is not found.", null);
        }

        if (answer.equals("Error writing file.")){
            return new Response(false, "Error writing file.", null);
        }

        return new Response(true, answer, null);
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "saves collection to file";
    }
}
