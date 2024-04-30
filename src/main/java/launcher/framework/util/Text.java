package launcher.framework.util;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Text {

    public static final char INFINITY = '\u221e';

    public static final String fString(final String input, int fSize) {
        if (input==null) {
            return fString("", fSize);
        }

        if (input.length() < fSize) {
            final StringBuilder sb = new StringBuilder();
            for(int i=input.length(); i<fSize; i++) {
                sb.append(' ');
            }
            return input + sb.toString();
        } else {
            return input.substring(0, fSize);
        }
    }

    public static <T> String describeSet(Set<T> items) { return describeSet(items, true);   }
    public static <T> String describeSet(Set<T> items, boolean isOneLine) {
        final List<T> itemsList = items.stream().collect(Collectors.toList());
        return describeList(itemsList, isOneLine);
    }

    public static <T> String describeList(List<T> items) { return describeList(items, true);   }
    public static <T> String describeList(List<T> items, boolean isOneLine) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<items.size(); i++) {
            if (i>0) {
                sb.append(",");
                if (isOneLine) {
                    sb.append(" ");
                } else {
                    sb.append("\n");
                }
            }

            sb.append(items.get(i));
        }
        final String content = (isOneLine) ? sb.toString() : indent(sb.toString());
        if (isOneLine) {
            return "{" + content + "}";
        } else {
            return "{\n" + content + "\n}\n";
        }
    }

    public static final String describeObject(Object obj) {
        if (obj==null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    public static final String describe(Point2D p) {
        return String.format("{%3.2f, %3.2f}", p.getX(), p.getY());
    }

    public static final String describe(Dimension d) {
        return String.format("<%3.2f, %3.2f>", d.getWidth(), d.getHeight());
    }

    public static final String describe(Rectangle2D r) {
        return String.format("[ {%3.2f, %3.2f}, <%3.2f, %3.2f> ]", r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public static final String describe(Point p) {
        return String.format("(%3d, %3d)", p.x, p.y);
    }

    public static class Range {
        int start;
        int end;

        public Range(final int start, final int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static final Optional<Range> findFirstMatch(final String regex, final String input) {
        final Pattern p = Pattern.compile(regex);   // the pattern to search for
        final Matcher m = p.matcher(input);
        if (m.find()) {
            final Range r = new Range(m.start(), m.end());
            return Optional.of(r);
        } else {
            return Optional.empty();
        }
    }

    public static final String indent(final String input) {
        final StringBuilder sb = new StringBuilder();
        if (input!=null) {
            boolean addIndent = true;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (addIndent) {
                    sb.append('\t');
                }
                sb.append(c);
                addIndent = (c == '\n');
            }
        }
        return sb.toString();
    }

    public static final String asObject(final List<String> lines) {
        final String body = describeList(lines, false);
        final String content = indent(body);
        return "{\n" + content + "}\n";
    }

    public static final String asObject(final Map<String, String> map) {
        final List<String> lines = new ArrayList<>();

        final List<String> sortedKeys = map.keySet().stream().sorted().collect(Collectors.toList());

        for(String key : sortedKeys) {
            final String value = map.get(key);
            final String line = asPair(key, value);
            lines.add(line);
        }

        return asObject(lines);
    }

    public static final String asPair(final Object key, final Object value) {
        return "" + Objects.toString(key) + ": " + Objects.toString(value);
    }
}
