package main.framework.util;

import launcher.framework.util.Logger;
import launcher.framework.util.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UtilTest {

    @Before
    public void onSetup() {

    }

    @After
    public void onTearDown() {

    }

    @Test
    public void describeList_multiLine() {
        final List<String> lines = createLines(10);
        final String output = Text.describeList(lines, false);
        Logger.info(output);
    }

    @Test
    public void describeList_oneLine() {
        final List<String> lines = createLines(10);
        final String output = Text.describeList(lines, true);
        Logger.info(output);
    }


    public static final List<String> createLines(final int numLines) {
        final List<String> lines = new ArrayList<>();
        for(int i=0; i<numLines; i++) {
            lines.add("Line[" + i + "]");
        }
        return lines;
    }
}


