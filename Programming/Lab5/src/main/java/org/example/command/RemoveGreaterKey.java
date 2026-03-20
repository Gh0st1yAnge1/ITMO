package org.example.command;

import org.example.manager.CollectionManager;

public class RemoveGreaterKey implements Command{

    private final CollectionManager collectionManager;

    public RemoveGreaterKey(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 1){
            System.out.println("Usage: remove_greater_key <key>");
            return;
        }

        try{
            Integer id = Integer.parseInt(args[0]);
            int result = collectionManager.removeGreaterKey(id);
            if(result == 0){
                System.out.println("Collection size didn't change.");
            } else {
                System.out.println("Successfully removed!");
                System.out.println("Number of removed elements: " + result);
            }
        } catch (NumberFormatException ex){
            System.out.println("Key must be integer.");
        }

    }

    @Override
    public String getName() {
        return "remove_greater_key";
    }

    @Override
    public String getDescription() {
        return "removes all collection elements,\nwhich key is more than inserted value";
    }
}
