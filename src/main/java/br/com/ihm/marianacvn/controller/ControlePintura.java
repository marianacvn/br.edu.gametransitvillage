package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.model.Camada;
import br.com.ihm.marianacvn.model.Inimigo;
import br.com.ihm.marianacvn.model.Logica;
import br.com.ihm.marianacvn.model.Personagem;
import br.com.ihm.marianacvn.view.MainFrame;

import java.awt.*;
import java.util.List;

public class ControlePintura implements Runnable{

	private MainFrame mainFrame;
	private List<Personagem> personagens;
	private Logica logica;
	private ControlePersonagem controlePersonagem;
	private ControleInimigos controleInimigos;
	private Inimigo resultado;
	private Graphics g;
	private Camada camadaFundo, camadaColisao, camadaTopo;
	public static List<Rectangle> colisao;
	private Thread thread;
	private boolean multplayer;
	
	public ControlePintura(MainFrame mainFrame, List<Personagem> personagens, Logica logica,
			ControlePersonagem controlePersonagem, ControleInimigos controleInimigos, Inimigo resultado) {
		this.mainFrame = mainFrame;
		this.personagens = personagens;
		this.logica = logica;
		this.controlePersonagem = controlePersonagem;
		this.controleInimigos = controleInimigos;
		this.resultado = resultado;
		
		this.camadaFundo = logica.getCamadaFundo();
		this.camadaColisao = logica.getCamadaColisao();
		this.camadaTopo = logica.getCamadaTopo();

		colisao = camadaColisao.montarColisao();
		
		this.g = mainFrame.getPanelByKey("map").getGraphics();
		
		mainFrame.addKeyListener(controlePersonagem);
		
		thread = new Thread(this);
		
		thread.start();
	}
	
	private void atualizarCamadas() {
		this.camadaFundo = logica.getCamadaFundo();
		this.camadaColisao = logica.getCamadaColisao();
		this.camadaTopo = logica.getCamadaTopo();
		colisao = camadaColisao.montarColisao();
	}
	
	public void draw() {
		g.drawImage(camadaFundo.camada, 0, 0, null);
		g.drawImage(camadaColisao.camada, 0, 0, null);

		for (Inimigo enemy : personagens.get(0).getInimigo()) {
			g.drawString(enemy.getAparencia(), enemy.getX()+5, enemy.getY()+25);
		}
		g.drawString(resultado.getAparencia(), resultado.getX()+5, resultado.getY()+25);
		g.drawImage(personagens.get(0).getSprites()[personagens.get(0).getAparencia()], personagens.get(0).getX(), personagens.get(0).getY(), null);
		if (multplayer)
			g.drawImage(personagens.get(1).getSprites()[personagens.get(1).getAparencia()], personagens.get(1).getX(), personagens.get(1).getY(), null);
		else
			g.drawImage(personagens.get(1).getSprites()[personagens.get(1).getAparencia()], 1000, 1000, null);
		g.drawImage(camadaTopo.camada, 0, 0, null);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		
	}
	
	public void update(){
		atualizarCamadas();
		camadaFundo.montarMapa();
		camadaColisao.montarMapa();
		camadaTopo.montarMapa();
	}
	
	public void morrer() {
		if (personagens.get(0).getVida() == 0 || personagens.get(1).getVida() == 0) {
			thread.stop();
		}
	}
	
	public void ganhou() {
		if (logica.isGanhou()) {
			thread.stop();
		}
	}
	
	@Override
	public void run() {
		
		while(true) {
			mainFrame.getPanelByKey("map").repaint();
			update();
			draw();
//			tela.getInventario().repaint();
			logica.iniciarFase();
			morrer();
			ganhou();
		}
		
	}

	public Thread getThread() {
		return thread;
	}

	public ControleInimigos getControleInimigos() {
		return controleInimigos;
	}

	public void setControleInimigos(ControleInimigos controleInimigos) {
		this.controleInimigos = controleInimigos;
	}

	public boolean isMultplayer() {
		return multplayer;
	}

	public void setMultplayer(boolean multplayer) {
		this.multplayer = multplayer;
	}
	
}
