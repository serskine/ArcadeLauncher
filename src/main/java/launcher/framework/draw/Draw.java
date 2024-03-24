package launcher.framework.draw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Draw {

    public static Rectangle2D toRectFromCenter(Point2D p, double radius) {
        return toRectFromCenter(p.getX(), p.getY(), radius);
    }

    public static Rectangle2D toRectFromCenter(double cX, double cY, double radius) {
        return toRectFromCenter(cX, cY, radius*2, radius*2);
    }

    public static Rectangle2D toRectFromCenter(double cX, double cY, double w, double h) {
        return new Rectangle2D.Double(cX - w/2D, cY - h/2D, w, h);
    }

    public static Point toPoint(Point2D p) {
        return new Point((int) p.getX(), (int) p.getY());
    }
    public static final void drawText(Graphics2D g, final Point2D p, final int height, final String text) {
        renderRetroText(g, p, height, g.getColor(), null, text);
    }

    public static final void fillText(Graphics2D g, final Point2D p, final int height, final String text) {
        renderRetroText(g, p, height, null, g.getColor(), text);
    }

    public static final Dimension getTextSize(Graphics2D g, final int height, final String text) {
        final Rectangle r = renderRetroText(g, new Point(0,0), height, null, null, text);
        return r.getSize();
    }

    public static final Rectangle renderRetroText(
            final Graphics2D g,
            final Point2D p2D,
            final int height,
            final Color lineColor,  // If null then the lines are not drawn
            final Color fillColor,   // If null then the fill is not painted
            final String text
    ) {
        final Point p = toPoint(p2D);
        String[] lines = text.split("\n");
        Rectangle[] boxes = new Rectangle[lines.length];

        int maxW = 0;
        for(int i=0; i<lines.length; i++) {
            Point newP = new Point(p.x, p.y + height * i);
            boxes[i] = renderRetroTextOneLine(g, newP, height-1, lineColor, fillColor, lines[i]);
            maxW = Math.max(maxW, boxes[i].width);
        }
        int maxH = lines.length * height;
        return new Rectangle(p.x, p.y, maxW, maxH);
    }

    private static final Rectangle renderRetroTextOneLine(
            final Graphics2D g,
            final Point p,
            final int height,
            final Color lineColor,  // If null then the lines are not drawn
            final Color fillColor,   // If null then the fill is not painted
            final String text
    ) {

        Color prevColor = null;

        if (g!=null) {
            prevColor = g.getColor();
        }

        int textWidth = 0;
        int textHeight = 0;

        if (text != null && text.length()>0) {
            final int unit = height / 5;

            boolean[][] flags = RetroChar.getFlagsOf(text, RetroChar.SPACE);
            int maxRowW = 0;

            for(int r=0; r<flags.length; r++) {
                final boolean[] row = flags[r];
                int rowW = 0;
                for(int c=0; c<row.length; c++) {
                    rowW += unit;
                    if (g!=null && row[c]==true) {
                        final int pixelX = p.x + c * unit;
                        final int pixelY = p.y + r * unit;

                        if (g != null && fillColor != null) {
                            g.setColor(fillColor);
                            g.fillRect(pixelX, pixelY, unit, unit);
                        }
                        if (g != null && lineColor != null) {
                            g.setColor(lineColor);
                            boolean drawNorth = (r==0               || flags[r-1][c]==false);
                            boolean drawSouth = (r==flags.length-1  || flags[r+1][c]==false);
                            boolean drawEast =  (c==row.length-1    || flags[r][c+1]==false);
                            boolean drawWest =  (c==0               || flags[r][c-1]==false);
                            drawRect(g, pixelX, pixelY, unit, unit, drawNorth, drawEast, drawSouth, drawWest);
                        }
                    }
                }
                maxRowW = Math.max(maxRowW, rowW);
            }

            textHeight = flags.length * unit;
            textWidth = maxRowW;
        }

        final Rectangle rect = new Rectangle(p.x, p.y, textWidth, textHeight);
        if (g!=null) {
            g.setColor(prevColor);
        }

        // This will return the rectangle area that contains the text on the screen.
        return rect;
    }

    public static final void drawX(final Graphics g, Rectangle2D sourceR) {
        drawX(g, sourceR, g.getColor());
    }

    public static final void drawX(
            final Graphics g,
            final Rectangle2D sourceR,
            final Color color
    ) {
        Color oldColor = g.getColor();
        g.setColor(color);
        Rectangle r = new Rectangle(
                (int) sourceR.getX(),
                (int) sourceR.getY(),
                (int) sourceR.getWidth(),
                (int) sourceR.getHeight());
        g.drawLine(r.x, r.y, r.x+r.width, r.y+r.height);
        g.drawLine(r.x + r.width, r.y, r.x, r.y+r.height);
        g.setColor(oldColor);
    }


    public static final void drawRect(
            final Graphics2D G,
            final Rectangle2D r
    ) {
        drawRect(G, r, G.getColor());
    }
    public static final void drawRect(
            final Graphics2D G,
            final Rectangle2D r,
            final Color color
    ) {
        final Color oldColor = G.getColor();
        G.setColor(color);
        drawRect(G, (int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight(), true, true, true, true);
        G.setColor(oldColor);
    }

    public static final void drawOval(final Graphics2D G, final Rectangle2D r, Color color) {
        Color oldColor = G.getColor();
        G.setColor(color);
        int rx = (int) r.getX();
        int ry = (int) r.getY();
        int rw = (int) r.getWidth();
        int rh = (int) r.getHeight();

        G.drawOval(rx, ry, rw, rh);
        G.setColor(oldColor);
    }

    public static final void drawRect(
        final Graphics g,
        final int x1,
        final int y1,
        final int width,
        final int height,
        final boolean renderNorth,
        final boolean renderEast,
        final boolean renderSouth,
        final boolean renderWest
    ) {
        final int x2 = x1 + width;
        final int y2 = y1 + height;

        if (g!=null) {
            if (renderNorth)    {   g.drawLine(x1, y1, x2, y1); }
            if (renderEast)     {   g.drawLine(x2, y1, x2, y2); }
            if (renderSouth)    {   g.drawLine(x2, y2, x1, y2); }
            if (renderWest)     {   g.drawLine(x1, y2, x1, y1); }
        }
    }

    public static void drawPoly(Graphics g, Point2D.Double... p) {
        int[] xPoints = new int[p.length];
        int[] yPoints = new int[p.length];

        for(int i=0; i<p.length; i++) {
            xPoints[i] = (int) p[i].getX();
            yPoints[i] = (int) p[i].getY();
        }

        g.drawPolygon(xPoints, yPoints, p.length);
    }

    public static void fillPoly(Graphics2D g, Point2D... p) {
        int[] xPoints = new int[p.length];
        int[] yPoints = new int[p.length];

        for(int i=0; i<p.length; i++) {
            xPoints[i] = (int) p[i].getX();
            yPoints[i] = (int) p[i].getY();
        }

        g.fillPolygon(xPoints, yPoints, p.length);
    }

    public static void drawLine(Graphics2D g, Point2D p1, Point2D p2, Color color) {
        if (color != null) {
            Color oldColor = g.getColor();
            g.setColor(color);
            double x1 = p1.getX();
            double y1 = p1.getY();
            double x2 = p2.getX();
            double y2 = p2.getY();

            int dx1 = (int) x1;
            int dy1 = (int) y1;
            int dx2 = (int) x2;
            int dy2 = (int) y2;

            g.drawLine(dx1, dy1, dx2, dy2);

            g.setColor(oldColor);
        }
    }


    public static void drawImage(Graphics G, Rectangle2D screenBounds, BufferedImage img) {
        G.drawImage(
                img,
                (int) screenBounds.getX(),
                (int) screenBounds.getY(),
                (int) screenBounds.getWidth(),
                (int) screenBounds.getHeight(),
                null);
    }

    public static Rectangle2D toRectangle(Point2D point) {
        return new Rectangle2D.Double(point.getX(), point.getY(), 1D, 1D);
    }

    public static final void fillRect(
            final Graphics2D G,
            final Rectangle2D r,
            Color color
    ) {
        Color oldColor = G.getColor();
        G.setColor(color);
        G.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
        G.setColor(oldColor);
    }

}
