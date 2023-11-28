package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.model.*;
import br.com.ihm.marianacvn.view.*;
import br.com.ihm.marianacvn.view.components.GameButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class GameController extends KeyAdapter implements ActionListener {
	MainFrame mainFrame;
	MapPanel mapPanel;
	Logica logica;

	Personagem personagem;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[1]; // TODO : Mudar para monitor 0

	boolean cima, baixo, direita, esquerda;
	int up, down, left, right;

	public GameController() throws IOException {
		createAndShowGUI();
	}

	private void createAndShowGUI() throws IOException {
		mainFrame = new MainFrame();
		logica = new Logica();
		personagem = new Personagem(8, 64, 64, 13, 21, 30, 500, "/assets/images/sprite/character-female_universal.png");

		mapPanel = (MapPanel) mainFrame.getPanelByKey("map");
		mapPanel.setLogica(logica);
		mapPanel.setPersonagem(personagem);

//		logica.getCamada("colision").montarColisao();

		device.setFullScreenWindow(mainFrame);

		for (GameButton button : mainFrame.buttons) {
			button.addActionListener(this);
		}

		mainFrame.addKeyListener(this);

		mainFrame.setFocusable(true);

		run();
	}

	public void montarMapa() {
		for (Camada camada : logica.getCamadas()) {
			camada.montarMapa();
		}
	}

	public void run() {
		Timer timer = new Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				montarMapa();
				mapPanel.repaint();
			}
		});

		timer.start();
	}


	@Override
	public void actionPerformed(ActionEvent e) {

//		// Game
		if (mainFrame.getButtonByKey("jogar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("start")) {
			mainFrame.getPanelByKey("start").setVisible(false);
			mainFrame.getPanelByKey("map").setVisible(true);
			mainFrame.getButtonByKey("jogar").setVisible(false);
			mainFrame.getButtonByKey("voltar").setVisible(false);
		}
//		// Menu Buttons
		if (mainFrame.getButtonByKey("novo") == e.getSource()) {
			mainFrame.disableMenuComponents("new-game");
		}
		if (mainFrame.getButtonByKey("jogar") == e.getSource()) {
			mainFrame.disableMenuComponents("start");
			mainFrame.getButtonByKey("jogar").setVisible(true); // TODO: remove this line
		}
		if (mainFrame.getButtonByKey("tutorial") == e.getSource()) {
			mainFrame.disableMenuComponents("help");
		}
		if (mainFrame.getButtonByKey("creditos") == e.getSource()) {
			mainFrame.disableMenuComponents("credit");
		}
		if (mainFrame.getButtonByKey("sair") == e.getSource()) {
			System.exit(0);
		}
//		// Voltar
		if (mainFrame.getButtonByKey("voltar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("start")) {
			mainFrame.enableMenuComponents("start");
		}
		if (mainFrame.getButtonByKey("voltar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("new-game")) {
			mainFrame.enableMenuComponents("new-game");
		}
		if (mainFrame.getButtonByKey("voltar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("help")) {
			mainFrame.enableMenuComponents("help");
		}
		if (mainFrame.getButtonByKey("voltar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("credit")) {
			mainFrame.enableMenuComponents("credit");
		}

	}


	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.out.println("Pause game");
		}

		if (e.getKeyCode() == KeyEvent.VK_W) cima = true;
		if (e.getKeyCode() == KeyEvent.VK_S) baixo = true;
		if (e.getKeyCode() == KeyEvent.VK_A) esquerda = true;
		if (e.getKeyCode() == KeyEvent.VK_D) direita = true;

		movimento();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) cima = false;
		if (e.getKeyCode() == KeyEvent.VK_S) baixo = false;
		if (e.getKeyCode() == KeyEvent.VK_A) esquerda = false;
		if (e.getKeyCode() == KeyEvent.VK_D) direita = false;
	}

	public void movimento() {
		if (esquerda) {
			int xL = personagem.getX();
			int yL = personagem.getY();
			if (xL > 0) {
				personagem.setX(xL - Personagem.VELOCIDADE);
				personagem.setY(yL);
				switch (left) {
					case 0:
						personagem.setAparencia(9);
						break;
					case 1:
						personagem.setAparencia(30);
						break;
					case 2:
						personagem.setAparencia(51);
						break;
					case 3:
						personagem.setAparencia(72);
						break;
					case 4:
						personagem.setAparencia(93);
						break;
					case 5:
						personagem.setAparencia(114);
						break;
					case 6:
						personagem.setAparencia(135);
						break;
					case 7:
						personagem.setAparencia(156);
						break;
					case 8:
						personagem.setAparencia(177);
						break;
					case 9:
						personagem.setAparencia(198);
						break;
					case 10:
						personagem.setAparencia(219);
						break;
				}
				if (left == 10) {
					left = 9;
				} else {
					left++;
				}

			}
		}
		if (cima) {
			int xL = personagem.getX();
			int yL = personagem.getY();
			if (yL > 0) {
				personagem.setX(xL);
				personagem.setY(yL - Personagem.VELOCIDADE);
				switch (up) {
					case 0:
						personagem.setAparencia(8);
						break;
					case 1:
						personagem.setAparencia(29);
						break;
					case 2:
						personagem.setAparencia(50);
						break;
					case 3:
						personagem.setAparencia(71);
						break;
					case 4:
						personagem.setAparencia(92);
						break;
					case 5:
						personagem.setAparencia(113);
						break;
					case 6:
						personagem.setAparencia(134);
						break;
					case 7:
						personagem.setAparencia(155);
						break;
					case 8:
						personagem.setAparencia(176);
						break;
					case 9:
						personagem.setAparencia(197);
						break;
					case 10:
						personagem.setAparencia(218);
						break;
				}
				if (up == 10) {
					up = 8;
				} else {
					up++;
				}
			}

		}
		if (direita) {
			int xL = personagem.getX();
			int yL = personagem.getY();
			if (xL < 1856) {
				personagem.setX(xL + Personagem.VELOCIDADE);
				personagem.setY(yL);
				switch (right) {
					case 0:
						personagem.setAparencia(11);
						break;
					case 1:
						personagem.setAparencia(32);
						break;
					case 2:
						personagem.setAparencia(53);
						break;
					case 3:
						personagem.setAparencia(74);
						break;
					case 4:
						personagem.setAparencia(95);
						break;
					case 5:
						personagem.setAparencia(116);
						break;
					case 6:
						personagem.setAparencia(137);
						break;
					case 7:
						personagem.setAparencia(158);
						break;
					case 8:
						personagem.setAparencia(179);
						break;
					case 9:
						personagem.setAparencia(200);
						break;
					case 10:
						personagem.setAparencia(221);
						break;
				}
				if (right == 10) {
					right = 11;
				} else {
					right++;
				}
			}
		}
		if (baixo) {
			int xL = personagem.getX();
			int yL = personagem.getY();
			if (yL < 1016) {
				personagem.setX(xL);
				personagem.setY(yL + Personagem.VELOCIDADE);
				switch (down) {
					case 0:
						personagem.setAparencia(10);
						break;
					case 1:
						personagem.setAparencia(31);
						break;
					case 2:
						personagem.setAparencia(52);
						break;
					case 3:
						personagem.setAparencia(73);
						break;
					case 4:
						personagem.setAparencia(94);
						break;
					case 5:
						personagem.setAparencia(115);
						break;
					case 6:
						personagem.setAparencia(136);
						break;
					case 7:
						personagem.setAparencia(157);
						break;
					case 8:
						personagem.setAparencia(178);
						break;
					case 9:
						personagem.setAparencia(199);
						break;
					case 10:
						personagem.setAparencia(220);
						break;
				}
				if (down == 10) {
					down = 10;
				} else {
					down++;
				}

			}
		}
		System.out.println("X: " + personagem.getX() + " Y: " + personagem.getY());
	}


}
