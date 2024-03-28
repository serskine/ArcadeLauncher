package launcher;

import launcher.framework.util.Logger;
import launcher.menu.MenuGame;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

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
            final String[] nativeLibraries = {"jinput-dx8_64.dll", "jinput-raw_64.dll"};

            for (String library : nativeLibraries) {
                loadNativeLibrary(library);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load libraries.", e);
        }
    }

    private static void loadNativeLibrary(String library) throws IOException {
        // Get the input stream for the native library
        InputStream inputStream = ArcadeLauncher.class.getResourceAsStream("/lib/" + library);
        if (inputStream == null) {
            throw new FileNotFoundException("Native library " + library + " not found in resources.");
        }

        // Create a temporary file to copy the native library
        File tempFile = File.createTempFile(library, null);
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
        System.load(tempFile.getAbsolutePath());

        Logger.info("Loaded native library: " + library);
    }
}
