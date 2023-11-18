package br.com.ihm.marianacvn.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class BasePanel extends JPanel{
	private String key;
	private BufferedImage buffBackground;
	private Graphics graphics;
	public ImageIcon backgroundImage;

	public BasePanel(String key, String imagePath) {
		this.key = key;
		setSize(BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT);
		setLocation(0, 0);
		setLayout(null);

		backgroundImage = new ImageIcon(Objects.requireNonNull(BaseFrame.class.getResource(imagePath)));
		buffBackground = new BufferedImage(BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		graphics = buffBackground.getGraphics();

		setVisible(false);
		
	}

	public BasePanel(String key) {
		this.key = key;
		setSize(BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT);
		setLocation(0, 0);
		setLayout(null);

		buffBackground = new BufferedImage(BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		graphics = buffBackground.getGraphics();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(buffBackground, 0, 0, null);
		
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public String getKey() {
		return key;
	}
}
