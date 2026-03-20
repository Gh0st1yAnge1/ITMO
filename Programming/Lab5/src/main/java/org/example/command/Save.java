package org.example.command;

import org.example.manager.CollectionManager;
import org.example.manager.FileManager;
import java.util.LinkedHashMap;

public class Save implements Command{

    private final FileManager fileManager;
    private final CollectionManager collectionManager;

    public Save(FileManager fileManager, CollectionManager collectionManager){
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length != 0){
            System.out.println("Usage: save");
            return;
        }
        System.out.println(fileManager.saveCollection(new LinkedHashMap<>(collectionManager.show())));
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "saves collection to file";
    }
}
