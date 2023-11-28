package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.model.Camada;
import br.com.ihm.marianacvn.model.Logica;
import br.com.ihm.marianacvn.model.Personagem;

import java.awt.*;
import java.util.List;

public class MapPanel extends BasePanel {

	private Logica logica;
	private Personagem personagem;

	public MapPanel(String key) {
		super(key);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//
		g.drawImage(logica.getCamada("floor").camada, 0, 0, null);
		g.drawImage(logica.getCamada("colision").camada, 0, 0, null);

		g.drawImage(personagem.getSprites()[personagem.getAparencia()], personagem.getX(), personagem.getY(), null);
//		if (multplayer)
//			g.drawImage(personagens.get(1).getSprites()[personagens.get(1).getAparencia()], personagens.get(1).getX(), personagens.get(1).getY(), null);
//		else
//			g.drawImage(personagens.get(1).getSprites()[personagens.get(1).getAparencia()], 1000, 1000, null);
		g.drawImage(logica.getCamada("top").camada, 0, 0, null);

//		g.dispose();
	}


	public Logica getLogica() {
		return logica;
	}

	public void setLogica(Logica logica) {
		this.logica = logica;
	}

	public Personagem getPersonagem() {
		return personagem;
	}

	public void setPersonagem(Personagem personagem) {
		this.personagem = personagem;
	}
}
