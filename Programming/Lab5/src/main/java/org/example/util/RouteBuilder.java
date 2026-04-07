package org.example.util;

import org.example.manager.InputManager;
import org.example.model.Coordinates;
import org.example.model.Location;
import org.example.model.Route;

public class RouteBuilder {

    private final InputManager inputManager;

    public RouteBuilder(InputManager inputManager){
        this.inputManager = inputManager;
    }

    public Route buildRoute(){

        String name = inputManager.readRouteName();
        if (name == null){
            System.out.println("User stopped creating route.");
            return null;
        }
        Coordinates coordinates = inputManager.readCoordinates();
        if (coordinates == null){
            System.out.println("User stopped creating route.");
            return null;
        }
        Location from = inputManager.readLocation();
        Location to = inputManager.readLocation();
        Float distance = inputManager.readRouteDistance();
        if (distance == null){
            System.out.println("user stopped creating route.");
            return null;
        }
        return new Route(name, coordinates, from, to, distance);
    }
}
