package org.example.request_and_response;

import org.example.model.Route;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;

public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final boolean success;
    private final String message;
    private final LinkedHashMap<Integer, Route> collection;

    public Response(Boolean success, String message, LinkedHashMap<Integer, Route> collection){
        this.success = success;
        this.message = message;
        this.collection = collection;
    }

    public LinkedHashMap<Integer, Route> getCollection() {
        return collection;
    }

    public String getMessage() {
        return message;
    }

    public boolean getResult() {
        return success;
    }
}
