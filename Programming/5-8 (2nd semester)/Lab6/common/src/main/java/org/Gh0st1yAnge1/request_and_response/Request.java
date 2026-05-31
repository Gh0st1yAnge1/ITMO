package org.Gh0st1yAnge1.request_and_response;

import org.Gh0st1yAnge1.model.Route;

import java.io.Serial;
import java.io.Serializable;

public record Request(CommandType commandType, String argument, Route route) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
