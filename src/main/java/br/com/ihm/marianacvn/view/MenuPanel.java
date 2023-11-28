package br.com.ihm.marianacvn.view;

import java.awt.*;

public class MenuPanel extends BasePanel {
	public MenuPanel(String key, String imageBackground){
		super(key, imageBackground);
		setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(backgroundImage.getImage(), 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(" Desenvolvido por Mariana, 2023", 165, BaseFrame.DEFAULT_HEIGHT - 20);

//		g.dispose();
	}

}
