package br.com.ihm.marianacvn.view.components;

import br.com.ihm.marianacvn.view.NewGamePanel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.Objects;

@Getter
@Setter
public class GameComandoLabel extends JLabel {
    private String direcao;
    private boolean selected;

    public GameComandoLabel(String direcao, int x, int y, int width, int height) {
        this.direcao = direcao;
        this.setBounds(x, y, width, height);
        this.setIcon(new ImageIcon(Objects.requireNonNull(NewGamePanel.class.getResource("/assets/images/comandos/" + direcao + ".png"))));
        //this.setVisible(false);
    }

}
