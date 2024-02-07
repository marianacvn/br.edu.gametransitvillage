package br.com.ihm.marianacvn.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Getter
@Setter
public abstract class BasePanel extends JPanel {
    private String key;
    public ImageIcon backgroundImage;

    public BasePanel(String key, String imagePath) {
        this.key = key;
        setSize(BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT);
        setLocation(0, 0);

        backgroundImage = new ImageIcon(Objects.requireNonNull(BaseFrame.class.getResource(imagePath)));

        setVisible(false);
    }

    public BasePanel(String key, int x, int y, int width, int height) {
        this.key = key;
        setSize(width, height);
        setLocation(x, y);

        setVisible(false);
    }

    public BasePanel(String key, int x, int y) {
        this.key = key;
        setLocation(x, y);

        setVisible(false);
    }

}