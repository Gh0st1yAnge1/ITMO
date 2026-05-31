package org.Gh0st1yAnge1;

import org.Gh0st1yAnge1.client_commands.*;
import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.FileManager;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;

import java.util.*;

public class ServerCommandExecutor {

    private final Map<String, Command> commands = new LinkedHashMap<>();

    public ServerCommandExecutor(CollectionManager collectionManager, FileManager fileManager){
        commands.put("average_of_distance", new AverageOfDistance(collectionManager));
        commands.put("help", new Help(this));
        commands.put("info", new Info(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("remove_key", new RemoveKey(collectionManager, this));
        commands.put("remove_greater_key", new RemoveGreaterKey(collectionManager, this));
        commands.put("count_by_distance", new CountByDistance(collectionManager));
        commands.put("filter_less_than_distance", new FilterLessThanDistance(collectionManager));
        commands.put("insert", new Insert(collectionManager, this));
        commands.put("update", new Update(collectionManager, this));
        commands.put("replace_if_lower", new ReplaceIfLower(collectionManager, this));
        commands.put("remove_greater", new RemoveGreater(collectionManager, this));
        commands.put("save_server", new SaveServer(fileManager,collectionManager));
        commands.put("check_key", new CheckKey(collectionManager));
    }

    public Response execute(Request request){
        String name = request.commandType().toString().toLowerCase();
        Command command = commands.get(name);
        if (command == null){
            return new Response(false, "Unknown command. Type 'help' to see available commands", null);
        }
        return command.execute(request.argument(), request.route());
    }

    public Map<String, Command> getCommands(){
        return commands;
    }
}
