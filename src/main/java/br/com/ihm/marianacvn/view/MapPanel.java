package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.controller.GameController;
import br.com.ihm.marianacvn.model.Camada;
import br.com.ihm.marianacvn.model.GameLoop;
import br.com.ihm.marianacvn.model.Logica;
import br.com.ihm.marianacvn.model.Personagem;

import java.awt.*;
import java.util.List;

public class MapPanel extends BasePanel {

	private Logica logica;
	private Personagem personagem;
	private Personagem veiculo;
	private static final double ZOOM_LEVEL = 2.0;

	public MapPanel(String key) {
		super(key);
		setDoubleBuffered(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Create an off-screen image for double buffering
		Image offscreenImage = createImage(getWidth(), getHeight());
		Graphics offscreenGraphics = offscreenImage.getGraphics();

		// Draw on the off-screen image
		super.paintComponent(offscreenGraphics);
		Graphics2D g2d = (Graphics2D) offscreenGraphics;

		double xOffset = personagem.getX() - getWidth() / (2 * ZOOM_LEVEL);
		double yOffset = personagem.getY() - getHeight() / (2 * ZOOM_LEVEL);

		g2d.scale(ZOOM_LEVEL, ZOOM_LEVEL);
		g2d.translate(-xOffset, -yOffset);

		g2d.drawImage(logica.getCamada("floor").camada, 0, 0, null);
		g2d.drawImage(logica.getCamada("colision").camada, 0, 0, null);

		// Desenha as colisões, use apenas para testes
		showColisionRectangle(g2d);

		// Desenha o veículo a ser utilizado
		g2d.drawImage(veiculo.getSprites()[veiculo.getAparencia()], veiculo.getX(), veiculo.getY(), null);

		// Desenha o personagem
		g2d.drawImage(personagem.getSprites()[personagem.getAparencia()], personagem.getX(), personagem.getY(), null);

		g2d.drawImage(logica.getCamada("top").camada, 0, 0, null);

// TODO: FPS
		g2d.setColor(Color.WHITE);
		System.out.println(GameLoop.frameCount);
		g2d.setFont(BaseFrame.DEFAULT_FONT.deriveFont(Font.BOLD, 20));
		g2d.drawString("FPS: " + GameLoop.frameCount, 20, BaseFrame.DEFAULT_HEIGHT - 20);

		// Dispose the off-screen graphics
		offscreenGraphics.dispose();

		// Draw the off-screen image to the screen
		g.drawImage(offscreenImage, 0, 0, this);
	}

	private void showColisionRectangle(Graphics2D g2d) {
		g2d.setColor(Color.RED); // Cor dos retângulos
		for (Rectangle collisionRect : GameController.colisao) {
			g2d.drawRect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
		}
		g2d.drawRect(personagem.getX()-Personagem.DIFF_COLISAO, personagem.getY()-Personagem.DIFF_COLISAO, personagem.getLarguraPersonagem()+(Personagem.DIFF_COLISAO*2), personagem.getAlturaPersonagem()+Personagem.DIFF_COLISAO);
		g2d.drawRect(veiculo.getX()-Personagem.DIFF_COLISAO, veiculo.getY()-Personagem.DIFF_COLISAO, veiculo.getLarguraPersonagem()+(Personagem.DIFF_COLISAO*2), veiculo.getAlturaPersonagem()+Personagem.DIFF_COLISAO);
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

	public Personagem getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Personagem veiculo) {
		this.veiculo = veiculo;
	}
}
