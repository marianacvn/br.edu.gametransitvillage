package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.model.Inimigo;

public class ControleInimigos extends Thread{
	
	Inimigo[] inimigos;
	Inimigo resultado;
	private int velocidade = 0; 
	
	public ControleInimigos(Inimigo[] inimigos, Inimigo resultado) {
		this.inimigos = inimigos;
		this.resultado = resultado;
		start();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				resultado.mover();
				moverInimigos();
				Thread.sleep(velocidade);
			} catch (Exception e) {
			}
		}
	}
	
	public void moverInimigos() {
		for (Inimigo enemy : inimigos) {
			enemy.mover();
		}
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}
	
}
