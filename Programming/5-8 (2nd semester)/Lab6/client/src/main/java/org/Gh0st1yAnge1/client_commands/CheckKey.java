package org.Gh0st1yAnge1.client_commands;

import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;

public class CheckKey implements Command{

    @Override
    public Request execute(String args) {
        return new Request(CommandType.CHECK_KEY, args, null);
    }
}
