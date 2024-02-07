package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.controller.GameController;
import br.com.ihm.marianacvn.model.GameLoop;
import br.com.ihm.marianacvn.model.Logica;
import br.com.ihm.marianacvn.model.Personagem;
import br.com.ihm.marianacvn.view.components.GameButton;
import br.com.ihm.marianacvn.view.components.GameComandoLabel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ComandosPanel extends BasePanel {

	public List<GameComandoLabel> comandos;
	private final GameButton playButton;

	public static int COMAND_AREA_X = 277;
	public static int COMAND_AREA_Y = 14;
	public static int COMAND_AREA_WIDTH = BaseFrame.DEFAULT_WIDTH - 10;
	public static int COMAND_AREA_HEIGHT = 70;

	public ComandosPanel(String key, int x, int y, int width, int height) {
		super(key, x, y, width, height);
		setLayout(null);
		comandos = new ArrayList<>();

		comandos.add(new GameComandoLabel("direita", 14, 14, 52, 52));
		comandos.add(new GameComandoLabel("esquerda", 76, 14, 52, 52));
		comandos.add(new GameComandoLabel("cima", 138, 14, 52, 52));
		comandos.add(new GameComandoLabel("baixo", 200, 14, 52, 52));

		for (GameComandoLabel comando : comandos) {
			add(comando);
		}

		playButton = new GameButton("play", BaseFrame.DEFAULT_WIDTH -14 - 52, 14, 52);
		add(playButton);

		setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(5)); // Define a espessura da linha
		g2.drawRect(5, 5, COMAND_AREA_WIDTH, COMAND_AREA_HEIGHT);
		g2.drawRect(5, 5, 258, COMAND_AREA_HEIGHT);
	}

}
