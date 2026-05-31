package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.manager.ServerCommandExecutor;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.Response;

public class Help implements ClientCommand {

    private final ServerCommandExecutor serverCommandExecutor;

    public Help(ServerCommandExecutor serverCommandExecutor){
        this.serverCommandExecutor = serverCommandExecutor;
    }


    @Override
    public Response execute(String arg, Route route) {

        if (arg != null){
            return new Response(false, "Usage: help", null);
        }

        String answer = "Available commands:\n\n--execute_script--\nexecutes script\n";
        for (ClientCommand clientCommand : serverCommandExecutor.getCommands().values()){
            if (clientCommand.getName().equals("save") || clientCommand.getName().equals("check_key")){
                continue;
            }
            answer += " \n";
            answer += "--" + clientCommand.getName() + "--\n";
            answer += clientCommand.getDescription()+ "\n";
        }

        return new Response(true, answer, null);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "shows available commands";
    }
}
