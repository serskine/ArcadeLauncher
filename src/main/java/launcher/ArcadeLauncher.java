package launcher;

import launcher.framework.util.Logger;
import launcher.menu.MenuGame;

import java.io.*;

public class ArcadeLauncher {
    public static final String PROP_LIBRARY_PATH = "net.java.games.input.librarypath";
    public static void main(final String[] args) {

        Logger.info(ArcadeLauncher.class.getSimpleName() + " started.");

        final String libraryPath = new File("src/main/resources/lib").getAbsolutePath();

//        System.setProperty(PROP_LIBRARY_PATH, libraryPath);

        System.load(libraryPath + File.separator + "jinput-dx8_64.dll");
        System.load(libraryPath + File.separator + "jinput-raw_64.dll");

        final Thread gameThread = new Thread(new MenuGame());
        gameThread.start();

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Logger.error(e.getMessage(), e);
        }

        Logger.info(ArcadeLauncher.class.getSimpleName() + " complete.");
    }
}
