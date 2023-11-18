package br.com.ihm.marianacvn.view;

import java.awt.*;

public class CreditPanel extends BasePanel {
	public CreditPanel(String key, String imageBackground) {
		super(key, imageBackground);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, BaseFrame.DEFAULT_WIDTH, BaseFrame.DEFAULT_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(15, 110, 610, 300);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Cr�ditos", 255, 150);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("Jogo criado por: Davi de Lima das Neves", 175, 180);
		g.drawString("Projeto criado para a disciplina", 210, 200);
		g.drawString("Modelagem e Programa��o Orientada a Objetos (MPOO)", 125, 220);
		g.drawString("Professor: Richarlyson D'Emery", 210, 240);
		g.drawString("Universidade Federal Rural de Pernambuco - UFRPE", 135, 260);
		g.drawString("Unidade Acad�mica de Serra Talhada - UAST", 175, 280);
		g.dispose();
	}

}
