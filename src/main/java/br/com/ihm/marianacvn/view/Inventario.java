package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.exception.ViewException;
import br.com.ihm.marianacvn.model.InventoryInfo;
import br.com.ihm.marianacvn.model.Personagem;
import br.com.ihm.marianacvn.statics.InventoryInfos;
import br.com.ihm.marianacvn.utils.ErrorHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Inventario extends JPanel{
	private List<Personagem> personagens;

	private JButton sairButton;
	private String status = "Somando", status2 = "Somando";
	private boolean multplayer;
	
	public Inventario(List<Personagem> personagens) {
		setSize(125, 512);
		setLocation(514, 0);
		setLayout(null);

		this.personagens = personagens;

		sairButton = new JButton("Sair");
		sairButton.setBounds(535, 480, 80, 20);

		add(sairButton);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		try {
			if (multplayer) {
				multiPlayer(g);
			} else {
				singlePlayer(g);
			}
		} catch (ViewException | IOException e) {
			ErrorHandler.logAndExit(e);
		}
	}

	private void drawInventorySection(Graphics g) {
		Color marrom = new Color(57, 43, 30);
		g.setColor(marrom);
		g.fillRect(514, 0, 125, 512);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString("Inventário", 553, 15);
		g.drawLine(514, 17, BaseFrame.DEFAULT_WIDTH, 17);
	}
	
	public void singlePlayer(Graphics g) throws ViewException, IOException {
		drawInventorySection(g);
		for (InventoryInfo info : InventoryInfos.getSingleInventoryInfos(personagens)) {
			info.draw(g);
		}
	}

	public void multiPlayer(Graphics g) throws IOException {

		drawInventorySection(g);
		g.drawLine(514, BaseFrame.DEFAULT_HEIGHT /2, BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT /2);
		for (InventoryInfo info : InventoryInfos.getMultiInventoryInfos(personagens)) {
			info.draw(g);
		}

	}

	public JButton getSairButton() {
		return sairButton;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isMultplayer() {
		return multplayer;
	}

	public void setMultplayer(boolean multplayer) {
		this.multplayer = multplayer;
	}
	
}
