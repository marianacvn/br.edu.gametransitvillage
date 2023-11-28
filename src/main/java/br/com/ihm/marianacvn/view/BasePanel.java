package br.com.ihm.marianacvn.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class BasePanel extends JPanel{
	private String key;
	public ImageIcon backgroundImage;

	public BasePanel(String key, String imagePath) {
		this.key = key;
		setSize(BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT);
		setLocation(0, 0);

		backgroundImage = new ImageIcon(Objects.requireNonNull(BaseFrame.class.getResource(imagePath)));

		setVisible(false);
	}

	public BasePanel(String key) {
		this.key = key;
		setSize(BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT);
		setLocation(0, 0);

		setVisible(false);
	}

	public String getKey() {
		return key;
	}
}