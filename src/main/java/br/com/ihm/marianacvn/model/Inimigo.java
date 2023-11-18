package br.com.ihm.marianacvn.model;

import br.com.ihm.marianacvn.controller.ControlePintura;

import java.awt.*;
import java.util.List;

public class Inimigo{
	private int x, y;
	private int largura, altura;
	private String aparencia;
	private String direcao = "baixo";
	
	public Inimigo(int x, int y, String aparencia) {
		super();
		this.x = x;
		this.y = y;
		this.largura = 25;
		this.altura = 25;
		this.aparencia = aparencia;
	}

	public void mover() {
		switch (direcao) {
		case "cima":
			setY(getY() - 1);
			break;
		case "baixo":
			setY(getY() + 1);
			break;
		case "esquerda":
			setX(getX() - 1);
			break;
		case "direita":
			setX(getX() + 1);
			break;
		};
	}

	public boolean colisao(List<Rectangle> tmp, int x,int y) {

		Rectangle inimigo = new Rectangle(getX()+x, getY()+y, 
				getLargura(), getAltura());
		for(Rectangle rectangle : tmp) {
			if(rectangle.intersects(inimigo)){
				return true;
			}
		}
		return false;
	}

	private void mudardirecao() {
		String[] direcoes = new String[] {"cima", "baixo", "esquerda", "direita"};
		direcao = direcoes[NumerosAleatorios.gerarNumeroAleatorio(direcoes.length)];
	}

	public String getAparencia() {
		return aparencia;
	}

	public void setAparencia(String aparencia) {
		this.aparencia = aparencia;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAltura() {
		return altura;
	}
	
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	public int getLargura() {
		return largura;
	}
	
	public void setLargura(int largura) {
		this.largura = largura;
	}

	public void setX(int x) {
		if(!colisao(ControlePintura.colisao, x-getX(), 0))
			this.x = x;
		else {
			mudardirecao();
		}
	}

	public void setY(int y) {
		if(!colisao(ControlePintura.colisao, 0, y-getY()))
			this.y = y; 
		else {
			mudardirecao();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x+5, y+5, largura-10, altura-10);
	}
}
