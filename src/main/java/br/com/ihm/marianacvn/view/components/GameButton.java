package br.com.ihm.marianacvn.view.components;

import br.com.ihm.marianacvn.view.BaseFrame;

import javax.swing.*;
import java.util.Objects;

public class GameButton extends JButton {

    private String key;

    public GameButton(String key, int marginBottom, int marginLeft, int width, int height) {
        this.key = key;
        setBounds(
                ((BaseFrame.DEFAULT_WIDTH - isZero(width, BaseFrame.DEFAULT_WIDTH_BUTTON)) / 2) + marginLeft,
                ((BaseFrame.DEFAULT_HEIGHT - isZero(height, BaseFrame.DEFAULT_HEIGHT_BUTTON)) / 2) + marginBottom,
                isZero(width, BaseFrame.DEFAULT_WIDTH_BUTTON),
                isZero(height, BaseFrame.DEFAULT_HEIGHT_BUTTON)
        );
        setIcon(new ImageIcon(Objects.requireNonNull(GameButton.class.getResource(BaseFrame.DEFAULT_BUTTONS_PATH + "btn-" + key + ".png"))));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int isZero(int x, int xDefault) {
    	return x == 0 ? xDefault : x;
    }

}
