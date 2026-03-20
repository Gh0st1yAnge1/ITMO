package org.example;

import org.example.manager.*;
import org.example.util.Runner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        TerminalManager.enableRawMode();
        Runtime.getRuntime().addShutdownHook(
                new Thread(TerminalManager::disableRawMode)
        );
        Runner.run();
    }
}