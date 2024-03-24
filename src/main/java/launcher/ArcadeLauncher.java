package launcher;

import launcher.framework.util.Logger;
import launcher.menu.MenuGame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ArcadeLauncher {
    public static final String NATIVE_LIB_FOLDER = "/lib/native/";
    public static final String PROP_JAVA_PATH = "java.library.path";
    public static void main(final String[] args) {

        Logger.info(ArcadeLauncher.class.getSimpleName() + " started.");

        // Load the JInput native libraries
        loadNativeLibrary("jinput-dx8_64.dll");
        loadNativeLibrary("jinput-raw_64.dll");


        //
        // Execute the main launcher.menu game.
        //

        Logger.info(" - Loaded required libraries.");

        final Thread gameThread = new Thread(new MenuGame());
        gameThread.start();

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Logger.error(e.getMessage(), e);
        }

        Logger.info(ArcadeLauncher.class.getSimpleName() + " complete.");
    }

    private static void loadNativeLibrary(String libraryName) {
        try {
            // Get the absolute path to the native library within the resources folder
            String nativeLibraryPath = ArcadeLauncher.class.getResource(NATIVE_LIB_FOLDER + libraryName).getPath();

            // If running from a JAR, extract the library to a temporary directory
            if (nativeLibraryPath.contains("!")) {
                File tempDir = Files.createTempDirectory("nativeLibs").toFile();
                InputStream is = ArcadeLauncher.class.getResourceAsStream(NATIVE_LIB_FOLDER + libraryName);
                File tempFile = new File(tempDir, libraryName);
                Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                nativeLibraryPath = tempFile.getAbsolutePath();
            }

            // Load the native library
            System.load(nativeLibraryPath);
            Logger.info("Successfully loaded native library: " + libraryName);
        } catch (IOException e) {
            Logger.error("Failed to extract native library: " + libraryName, e);
        } catch (UnsatisfiedLinkError e) {
            Logger.error("Failed to load native library: " + libraryName, e);
        }
    }

}
