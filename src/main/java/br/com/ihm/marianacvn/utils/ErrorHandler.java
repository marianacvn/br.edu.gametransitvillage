package br.com.ihm.marianacvn.utils;

import java.util.logging.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());
    private static final String LOG_DIRECTORY = "./logs";
    private static final String LOG_FILE = LOG_DIRECTORY + "/application.log";

    static {
        try {
            Path logDirectoryPath = Paths.get(LOG_DIRECTORY);
            if (!Files.exists(logDirectoryPath)) {
                Files.createDirectories(logDirectoryPath);
            }
            Handler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to set up logger", e);
            System.exit(1);
        }
    }

    public static void logAndExit(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        System.exit(0);
    }
}