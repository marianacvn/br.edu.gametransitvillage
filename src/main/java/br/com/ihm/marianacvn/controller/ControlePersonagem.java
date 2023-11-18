package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.model.Personagem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class ControlePersonagem extends Thread implements KeyListener {

	private List<Personagem> personagens;
	private String direcao ="";
	private String direcao2 ="";
	private boolean taRodando;

	public ControlePersonagem(List<Personagem> personagens) {
		this.personagens = personagens;
		
		start();
		taRodando = true;
	}

	@Override
	public void run() {

		while(true) {
			try {
				personagens.get(0).mover(direcao);
//				personagens.get(1).mover(direcao2);
				personagens.get(0).perderVida();
//				personagens.get(1).perderVida();
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int codigo = e.getKeyCode();

		switch (codigo) {
		case KeyEvent.VK_UP:
			direcao = "cima";
			break;
		case KeyEvent.VK_DOWN:
			direcao = "baixo";
			break;
		case KeyEvent.VK_LEFT:
			direcao = "esquerda";
			break;
		case KeyEvent.VK_RIGHT:
			direcao = "direita";
			break;
		case KeyEvent.VK_W:
			direcao2 = "cima";
			break;
		case KeyEvent.VK_S:
			direcao2 = "baixo";
			break;
		case KeyEvent.VK_A:
			direcao2 = "esquerda";
			break;
		case KeyEvent.VK_D:
			direcao2 = "direita";
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int codigo = e.getKeyCode();

		switch (codigo) {
		case KeyEvent.VK_UP:
			direcao = "";
			break;
		case KeyEvent.VK_DOWN:
			direcao = "";
			break;
		case KeyEvent.VK_LEFT:
			direcao = "";
			break;
		case KeyEvent.VK_RIGHT:
			direcao = "";
			break;
		case KeyEvent.VK_W:
			direcao2 = "";
			break;
		case KeyEvent.VK_S:
			direcao2 = "";
			break;
		case KeyEvent.VK_A:
			direcao2 = "";
			break;
		case KeyEvent.VK_D:
			direcao2 = "";
			break;
		default:
			break;
		}


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
