package br.com.ihm.marianacvn.view;

import javax.swing.*;
import java.awt.*;

public class MinimapPanel extends BasePanel {
    private final MapPanel mapPanel;
    private static final float SCALE = 0.2f; // Ajuste conforme necessário

    public MinimapPanel(String key, int x, int y, int width, int height, MapPanel mapPanel) {
        super(key, x, y);
        this.mapPanel = mapPanel;

        setSize(width, height);
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Create an off-screen image for double buffering
        Image offscreenImage = createImage(getWidth(), getHeight());
        Graphics offscreenGraphics = offscreenImage.getGraphics();

        // Draw on the off-screen image
        super.paintComponent(offscreenGraphics);
        Graphics2D g2d = (Graphics2D) offscreenGraphics;

        g2d.scale(SCALE, SCALE);
        mapPanel.paintWithoutZoom(g2d);

        // Dispose the off-screen graphics
        offscreenGraphics.dispose();

        // Draw the off-screen image to the screen
        g.drawImage(offscreenImage, 0, 0, this);
    }
}