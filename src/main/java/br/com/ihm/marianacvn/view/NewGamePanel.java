package br.com.ihm.marianacvn.view;

import java.awt.*;

public class NewGamePanel extends BasePanel {
	public NewGamePanel(String key, String imageBackground) {
		super(key, imageBackground);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
		g.dispose();
	}

}
