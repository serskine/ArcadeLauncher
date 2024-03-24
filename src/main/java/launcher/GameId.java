package launcher;

import java.awt.*;
import java.util.Optional;

public enum GameId {
    DEBUGGER("debugger", null),
    JINPUT("jinput", null),
    MENU("menu", null);

    public final String name;
    public final Dimension windowSize;

    GameId(final String name, final Dimension windowSize) {
        this.name = name;
        this.windowSize = windowSize;
    }

    public static Optional<GameId> parse(final String name) {
        for(GameId gameId : GameId.values()) {
            if (gameId.name.equals(name)) {
                return Optional.of(gameId);
            }
        }
        return Optional.empty();
    }
}
