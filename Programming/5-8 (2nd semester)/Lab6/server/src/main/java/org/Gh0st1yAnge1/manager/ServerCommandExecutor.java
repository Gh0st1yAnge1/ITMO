package org.Gh0st1yAnge1.manager;

import org.Gh0st1yAnge1.audit.AuditProducer;
import org.Gh0st1yAnge1.client_commands.*;
import org.Gh0st1yAnge1.exceptions.InputCancelledException;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;
import org.Gh0st1yAnge1.server_commands.Exit;
import org.Gh0st1yAnge1.server_commands.SaveWithPath;
import org.Gh0st1yAnge1.server_commands.ServerCommand;
import org.Gh0st1yAnge1.server_commands.ServerHelp;

import java.util.*;

public class ServerCommandExecutor {

    private final Map<String, ClientCommand> clientCommands = new LinkedHashMap<>();
    private final Map<String, ServerCommand> serverCommands = new LinkedHashMap<>();
    private final AuditProducer auditProducer;
    private static final Set<CommandType> AUDITED_COMMANDS = Set.of(
            CommandType.INSERT,
            CommandType.UPDATE,
            CommandType.REMOVE_KEY,
            CommandType.REMOVE_GREATER_KEY,
            CommandType.REMOVE_GREATER,
            CommandType.REPLACE_IF_LOWER,
            CommandType.CLEAR,
            CommandType.SHOW
    );


    public ServerCommandExecutor(CollectionManager collectionManager, FileManager fileManager, AuditProducer auditProducer){
        this.auditProducer = auditProducer;
        clientCommands.put("average_of_distance", new AverageOfDistance(collectionManager));
        clientCommands.put("help", new Help(this));
        clientCommands.put("info", new Info(collectionManager));
        clientCommands.put("show", new Show(collectionManager));
        clientCommands.put("clear", new Clear(collectionManager));
        clientCommands.put("remove_key", new RemoveKey(collectionManager, this));
        clientCommands.put("remove_greater_key", new RemoveGreaterKey(collectionManager, this));
        clientCommands.put("count_by_distance", new CountByDistance(collectionManager));
        clientCommands.put("filter_less_than_distance", new FilterLessThanDistance(collectionManager));
        clientCommands.put("insert", new Insert(collectionManager, this));
        clientCommands.put("update", new Update(collectionManager, this));
        clientCommands.put("replace_if_lower", new ReplaceIfLower(collectionManager, this));
        clientCommands.put("remove_greater", new RemoveGreater(collectionManager, this));
        clientCommands.put("save_server", new SaveServer(fileManager,collectionManager));
        clientCommands.put("check_key", new CheckKey(collectionManager));

        serverCommands.put("exit", new Exit());
        serverCommands.put("save_with_path", new SaveWithPath(collectionManager));
        serverCommands.put("help_server", new ServerHelp(this));
    }

    public Response execute(Request request){
        String name = request.commandType().toString().toLowerCase();
        ClientCommand command = clientCommands.get(name);
        if (command == null){
            return new Response(false, "Unknown command. Type 'help' to see available commands", null);
        }

        Response response = command.execute(request.argument(), request.route());

        if (auditProducer != null && AUDITED_COMMANDS.contains(request.commandType())) {
            auditProducer.sendIfAuditable(
                    request.commandType().name(),
                    request.argument(),
                    response.success(),
                    response.message()
            );
        }

        return response;
    }

    public String execute(String input) {
        if (input == null || input.trim().isEmpty()) return null;

        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0];
        String arg = parts.length > 1 ? parts[1] : null;

        ServerCommand command = serverCommands.get(commandName);

        if (command == null) {
            System.out.println("Unknown command. Type 'help_server' to see available commands.");
            return null;
        }

        try {
            return command.execute(arg);
        } catch (InputCancelledException ex) {
            System.out.println("Command cancelled.");
        } catch (Exception e) {
            System.out.println("Error while executing command: " + e.getMessage());
        }
        return null;
    }

    public Map<String, ClientCommand> getCommands(){
        return clientCommands;
    }
    public Map<String, ServerCommand> getServerCommands() {return  serverCommands; }
}
