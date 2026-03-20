package org.example.command;

import org.example.manager.CollectionManager;

public class RemoveKey implements Command{

    private  final CollectionManager collectionManager;

    public RemoveKey(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1){
            System.out.println("Usage: remove_key <key>");
            return;
        }

        try{
            Integer id = Integer.parseInt(args[0]);
            if (collectionManager.removeByKey(id)){
                System.out.println("Element's removed.");
            } else {
                System.out.println("Key doesn't exist.");
            }
        } catch (NumberFormatException ex){
            System.out.println("Key must be integer.");
        }

    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return "removes element using a key";
    }
}
