package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.view.components.GameButton;
import br.com.ihm.marianacvn.view.components.GameFaseLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewGamePanel extends BasePanel {

	private final GameButton rightButton, leftButton;
	private final JLabel playerLabel;

	private final List<GameFaseLabel> fases = new ArrayList<>();

	private String currentPlayer = "male-player";
	private int count = 0;

	public NewGamePanel(String key, String imageBackground) {
		super(key, imageBackground);
		setLayout(null);

		playerLabel = new JLabel();
		playerLabel.setBounds(BaseFrame.CENTER_DEFAULT_X-64, 150, 100, 200);
		setPlayerImage();

		GameFaseLabel fase1 = new GameFaseLabel(1, false, "fase1");
		fase1.setBounds(BaseFrame.CENTER_DEFAULT_X-314-30, 500, 200, 200);
		fase1.setFaseImage();

		GameFaseLabel fase2 = new GameFaseLabel(2, true, "fase2-block");
		fase2.setBounds(BaseFrame.CENTER_DEFAULT_X-114, 500, 200, 200);
		fase2.setFaseImage();

		GameFaseLabel fase3 = new GameFaseLabel(3, true, "fase3-block");
		fase3.setBounds(BaseFrame.CENTER_DEFAULT_X+86+30, 500, 200, 200);
		fase3.setFaseImage();

		fases.add(fase1);
		fases.add(fase2);
		fases.add(fase3);

		rightButton = new GameButton("right-voltar", playerLabel.getX()+playerLabel.getWidth()+5, playerLabel.getY()+playerLabel.getHeight()-52, 52);
		leftButton = new GameButton("voltar", playerLabel.getX()-52-5, playerLabel.getY()+playerLabel.getHeight()-52, 52);

		add(playerLabel);
		for(GameFaseLabel fase : fases)
			add(fase);
		add(rightButton);
		add(leftButton);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
	}

	public void setPlayerImage() {
		if (count != 0 &&  currentPlayer.equals("male-player") ) {
			currentPlayer = "female-player";
			count= 0;
		} else {
			currentPlayer = "male-player";
			count++;
		}
		ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(NewGamePanel.class.getResource("/assets/images/" + currentPlayer + ".png")));
		playerLabel.setIcon(imageIcon);
	}

	public GameButton getRightButton() {
		return rightButton;
	}

	public GameButton getLeftButton() {
		return leftButton;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public List<GameFaseLabel> getFases() {
		return fases;
	}

	public GameFaseLabel getFaseAtiva() {
		for(GameFaseLabel fase : fases) {
			if(!fase.isBlocked() && fase.isSelected()) {
				return fase;
			}
		}
		return null;
	}

}
