package br.com.ihm.marianacvn.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoadingPanel extends BasePanel {

	ImageIcon loadingGif;
	JLabel loadingLabel;

	public LoadingPanel(String key, String imageBackground) {
		super(key, imageBackground);
		setLayout(null);
		loadingGif = new ImageIcon(Objects.requireNonNull(BaseFrame.class.getResource("/assets/images/carro.gif")));
		loadingLabel = new JLabel(loadingGif);
		loadingLabel.setBounds(BaseFrame.DEFAULT_WIDTH-loadingGif.getIconWidth()-50, BaseFrame.DEFAULT_HEIGHT-loadingGif.getIconHeight()+50, loadingGif.getIconWidth(), loadingGif.getIconHeight());

		add(loadingLabel);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
		g.setColor(Color.BLACK);
		g.setFont(BaseFrame.DEFAULT_FONT.deriveFont(Font.BOLD, 30));
		g.drawString("Carregando ...", 50, BaseFrame.DEFAULT_HEIGHT-70);
	}

}