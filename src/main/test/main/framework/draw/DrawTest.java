package main.framework.draw;

import launcher.framework.draw.Draw;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class DrawTest {

    static final int HEIGHT = 10;
    @Test
    public void getTextSize() {

        final String text = "The quick brown fox jumps over the lazy dog";

        final Dimension observed = Draw.getTextSize(HEIGHT, text);

        assertEquals(HEIGHT, observed.getHeight(), 0D);
        assertEquals(HEIGHT * text.length(), observed.getWidth(), 0D);

    }
}
