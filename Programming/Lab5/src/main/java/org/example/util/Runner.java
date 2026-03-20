package org.example.util;

import org.example.command.*;
import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.manager.FileManager;
import org.example.manager.InputManager;
import org.example.model.Route;

import java.util.LinkedHashMap;

public class Runner {

    public static void run(){
        String filePath = System.getenv("FILE_PATH");

        if (filePath == null){
            System.out.println("Path is not set");
            return;
        }

        FileManager fileManager = new FileManager(filePath);
        try{
            LinkedHashMap<Integer, Route> loadedCollection = fileManager.loadCollection();
            CollectionManager collectionManager = new CollectionManager();

            for (Integer key: loadedCollection.keySet()){
                collectionManager.insert(key, loadedCollection.get(key));
            }

            InputManager inputManager = new InputManager();
            RouteBuilder routeBuilder = new RouteBuilder(inputManager);
            CommandManager commandManager = new CommandManager(inputManager);

            commandManager.register(new Help(commandManager));
            commandManager.register(new Info(collectionManager));
            commandManager.register(new Show(collectionManager));
            commandManager.register(new Insert(collectionManager, routeBuilder, commandManager));
            commandManager.register(new Update(collectionManager, routeBuilder));
            commandManager.register(new RemoveKey(collectionManager));
            commandManager.register(new Clear(collectionManager, commandManager));
            commandManager.register(new Save(fileManager, collectionManager));
            commandManager.register(new RemoveGreater(collectionManager, routeBuilder, commandManager));
            commandManager.register(new ReplaceIfLower(collectionManager, routeBuilder));
            commandManager.register(new RemoveGreaterKey(collectionManager));
            commandManager.register(new AverageOfDistance(collectionManager));
            commandManager.register(new CountByDistance(collectionManager));
            commandManager.register(new FilterLessThanDistance(collectionManager));
            commandManager.register(new Exit());
            commandManager.register(new ExecuteScript(commandManager));
            System.out.println("Program started. Type 'help' to see commands.");

            while (true){
                System.out.print(">");

                String input = inputManager.readline();

                if (input == null){
                    continue;
                }

                if (input.isEmpty()){
                    continue;
                }

                commandManager.execute(input);
            }
        } catch (Exception e){
            System.out.println("Collection can't be loaded.");
        }
    }
}
