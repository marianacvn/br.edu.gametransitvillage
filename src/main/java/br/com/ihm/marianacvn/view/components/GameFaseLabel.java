package br.com.ihm.marianacvn.view.components;

import br.com.ihm.marianacvn.view.NewGamePanel;

import javax.swing.*;
import java.util.Objects;

public class GameFaseLabel extends JLabel {
    private int fase;
    private boolean isBlocked;
    private boolean selected;
    private String imageName;

    public GameFaseLabel(int fase, boolean isBlocked, String imageName) {
        this.fase = fase;
        this.isBlocked = isBlocked;
        this.imageName = imageName;
    }

    public void setFaseImage() {
        this.setIcon(new ImageIcon(Objects.requireNonNull(NewGamePanel.class.getResource("/assets/images/" + imageName + ".png"))));
    }

    public String getSpriteByFase() {
        return switch (fase) {
            case 1 -> "pizza";
            case 2 -> "bus";
            case 3 -> "taxi";
            default -> null;
        };
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "GameFaseLabel{" +
                "fase=" + fase +
                ", isBlocked=" + isBlocked +
                ", selected=" + selected +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
