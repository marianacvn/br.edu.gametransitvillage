package br.com.ihm.marianacvn.view;

import java.awt.*;

public class MapPanel extends BasePanel {
	public MapPanel(String key) {
		super(key);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.dispose();
	}

}
