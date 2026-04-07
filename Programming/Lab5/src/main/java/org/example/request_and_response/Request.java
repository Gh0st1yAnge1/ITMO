package org.example.request_and_response;

import org.example.model.Route;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final CommandType commandType;
    private final String argument;
    private final Route route;

    public Request(Route route, CommandType commandType, String argument) {
        this.route = route;
        this.argument = argument;
        this.commandType = commandType;
    }

    public Route getRoute() {
        return route;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getArgument() {
        return argument;
    }
}