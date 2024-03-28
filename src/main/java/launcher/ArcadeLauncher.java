package launcher;

import launcher.framework.util.Logger;
import launcher.menu.MenuGame;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

public class ArcadeLauncher {
    public static final String PROP_LIBRARY_PATH = "net.java.games.input.librarypath";
    public static void main(final String[] args) {

        Logger.info(ArcadeLauncher.class.getSimpleName() + " started.");

        loadLibraries();
        final Thread gameThread = new Thread(new MenuGame());
        gameThread.start();

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Logger.error(e.getMessage(), e);
        }

        Logger.info(ArcadeLauncher.class.getSimpleName() + " complete.");
    }

    private static void loadLibraries() {
        try {
            // Create a temporary directory
            String tempDirectoryPath = createTempDirectory();

            // Set the library path property to the temporary directory
            System.setProperty("net.java.games.input.librarypath", tempDirectoryPath);

            final String[] nativeLibraries = {"jinput-dx8_64.dll", "jinput-raw_64.dll"};

            for (String library : nativeLibraries) {
                // Load native libraries using the temporary directory
                loadNativeLibrary(library, tempDirectoryPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load libraries.", e);
        }
    }


    private static String createTempDirectory() throws IOException {
        // Create a temporary directory
        File tempDir = Files.createTempDirectory("native").toFile();

        // Mark the directory to be deleted when the JVM exits
        tempDir.deleteOnExit();

        // Return the absolute path of the temporary directory
        return tempDir.getAbsolutePath();
    }


    private static String loadNativeLibrary(String library, String targetDirectory) throws IOException {
        // Get the input stream for the native library
        InputStream inputStream = ArcadeLauncher.class.getResourceAsStream("/lib/" + library);
        if (inputStream == null) {
            throw new FileNotFoundException("Native library " + library + " not found in resources.");
        }

        // Create a temporary file in the specified directory to copy the native library
        File tempFile = new File(targetDirectory, library);
        tempFile.deleteOnExit();

        // Copy the native library from the input stream to the temporary file
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            inputStream.close();
        }

        // Load the native library from the temporary file
        final String tempFilePath = tempFile.getAbsolutePath();
        System.load(tempFilePath);

        Logger.info("Loaded native library: " + library + " from " + tempFilePath);
        return tempFilePath;
    }
}
