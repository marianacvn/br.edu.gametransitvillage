package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.model.*;
import br.com.ihm.marianacvn.view.*;
import br.com.ihm.marianacvn.view.components.GameButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrameController implements ActionListener{
	MainFrame mainFrame;
	private List<Personagem> personagens;
	private List<InventoryInfo> inventoryInfos;
	private Logica logica;
	private ControlePersonagem controlePersonagem;
	private ControleInimigos controleInimigos;
	private Inimigo resultado;
	private ControlePintura controlePintura;

	public FrameController() throws IOException {
		mainFrame = new MainFrame();

//		personagens = new ArrayList<>();
//		Inimigo resultado = new Inimigo(30, 176, "");
//		Personagem personagem = new Personagem(Inimigos.iniciarInimigos(), resultado, 0, 32, 64, 4, 3, 236, 236, "/assets/images/sprite/sprite-detective_universal.png");
//		Personagem personagem2 = new Personagem(Inimigos.iniciarInimigos(), resultado, 0, 32, 64, 4, 3, 236, 236, "/assets/images/sprite/sprite-detective_universal.png"); // TODO: gambiarra, remover depois
//
//		personagens.add(personagem);
//		personagens.add(personagem2);
//
//		controlePersonagem = new ControlePersonagem(personagens);

		for (GameButton button : mainFrame.buttons) {
			button.addActionListener(this);
		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
//		// Menu Buttons
		if (mainFrame.getButtonByKey("novo") == e.getSource()) {
			mainFrame.disableMenuComponents("new-game");
			//mainFrame.getButtonByKey("jogar").setVisible(true); // TODO: remove this line
		}
		if (mainFrame.getButtonByKey("jogar") == e.getSource()) {
			mainFrame.disableMenuComponents("start");
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
//
//		// Game
		if (mainFrame.getButtonByKey("jogar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("start")) {
//			System.out.println("jogar-iniciado");
//			boolean multplayer = false;
//			resultado = personagens.get(0).getResultado();
//			logica = new Logica(personagens, resultado);
//			controleInimigos = new ControleInimigos(personagens.get(0).getInimigo(), resultado);
//			controlePintura = new ControlePintura(mainFrame, personagens, logica, controlePersonagem, controleInimigos, resultado);
//			controlePintura.setMultplayer(multplayer);
//			tela.getInventario().setMultplayer(multplayer);
//			menu.setVisible(false);
//			mainFrame.getPanelByKey("start").setVisible(false);
//			mainFrame.getPanelByKey("map").setVisible(true);
//			mainFrame.getButtonByKey("jogar").setVisible(false);
//			mainFrame.getButtonByKey("seta-voltar").setVisible(false);
//			tela.setVisible(true);
//			tela.requestFocus();
		}
//		if (tela.getInventario().getSairButton() == e.getSource()) {
//			tela.setVisible(false);
//			menu.setVisible(true);
//			personagens.get(0).reset(236, 236);
//			personagens.get(1).reset(236, 280);
//			personagens.get(0).setInimigo(logica.resetarPosicaoInimigos());
//			personagens.get(1).setInimigo(personagens.get(0).getInimigo());
//			logica.setCamadaFundo(Camadas.fase1()[0]);
//			logica.setCamadaColisao(Camadas.fase1()[1]);
//			logica.setCamadaTopo(Camadas.fase1()[2]);
//			tela.getInventario().setStatus("Somando");
//			controlePintura.getThread().stop();
//			controleInimigos.stop();
//		}

	}

}
