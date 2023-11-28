package br.com.ihm.marianacvn.view;

import java.awt.*;

public class HelpPanel extends BasePanel {
	public HelpPanel(String key, String imageBackground) {
		super(key, imageBackground);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(backgroundImage.getImage(), 0, 0, null);
		g.setColor(new Color(0,65,109));
		g.setFont(BaseFrame.DEFAULT_FONT.deriveFont(Font.BOLD, 50));
		g.drawString("Tutorial", (BaseFrame.DEFAULT_WIDTH/2)-163, 164);

//		g.dispose();
	}

}
