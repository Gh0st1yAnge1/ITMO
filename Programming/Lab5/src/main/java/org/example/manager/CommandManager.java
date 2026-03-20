package org.example.manager;

import org.example.command.Command;
import org.example.exception.InputCancelledException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CommandManager {

    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final InputManager inputManager;
    private final Set<String> scriptStack = new HashSet<>();

    public CommandManager(InputManager inputManager){
        this.inputManager = inputManager;
    }

    public void register(Command command){
        commands.put(command.getName(), command);
    }

    public void execute(String input){

        if (input == null || input.trim().isEmpty()){
            return;
        }

        String[] parts = input.trim().split("\\s+");

        String commandName = parts[0];

        String[] args = Arrays.copyOfRange(parts,1,parts.length);

        Command command = commands.get(commandName);

        if (command == null){
            System.out.println("Unknown command. Type 'help' to see available commands.");
            return;
        }

        try{
            command.execute(args);
        } catch (InputCancelledException ex) {
            System.out.println("Command cancelled.");
        } catch (Exception e) {
            System.out.println("Error while executing command " + e.getMessage());
        }
    }

    public void executeScript(String fileName) {


        try {
            File file = new File(fileName);
            String canonicalPath = file.getCanonicalPath();

            if (scriptStack.contains(canonicalPath)){
                System.out.println("Recursive detected");
                scriptStack.clear();
                return;
            }

            if (!file.exists()) {
                System.out.println("Script file not found.");
                return;
            }

            scriptStack.add(canonicalPath);

            Scanner fileScanner = new Scanner(file);
            inputManager.pushScanner(fileScanner);

            try {
                while (true) {
                    String line = inputManager.readline();
                    if (line == null) break;
                    if (line.isEmpty()) continue;
                    System.out.println("> " + line);
                    execute(line);
                }

            } finally {
                inputManager.popScanner();
                fileScanner.close();
            }
        } catch (Exception e) {
            System.out.println("Error while executing script: " + e.getMessage());
        }
    }

    public Map<String, Command> getCommands(){
        return commands;
    }
}
