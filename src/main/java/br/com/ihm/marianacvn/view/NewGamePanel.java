package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.view.components.GameButton;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class NewGamePanel extends BasePanel {

	private final GameButton rightButton, leftButton;
	private final JLabel playerLabel;
	private String currentPlayer = "male-player";
	private int count = 0;

	public NewGamePanel(String key, String imageBackground) {
		super(key, imageBackground);
		setLayout(null);

		playerLabel = new JLabel();
		playerLabel.setBounds(BaseFrame.CENTER_DEFAULT_X-64, 150, 100, 200);
		setPlayerImage();

		rightButton = new GameButton("right-voltar", playerLabel.getX()+playerLabel.getWidth()+5, playerLabel.getY()+playerLabel.getHeight()-52, 52);
		leftButton = new GameButton("voltar", playerLabel.getX()-52-5, playerLabel.getY()+playerLabel.getHeight()-52, 52);

		add(playerLabel);
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

}
