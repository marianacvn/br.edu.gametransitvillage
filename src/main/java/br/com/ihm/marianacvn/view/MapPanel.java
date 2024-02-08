package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.controller.GameController;
import br.com.ihm.marianacvn.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class MapPanel extends BasePanel {

    private Logica logica;
    private Personagem personagem;
    private Veiculo veiculo;
    private static final double ZOOM_LEVEL = 2.0;
    private boolean isGameOver, isMissaoConcluida;
    private GameController gameController;

    public MapPanel(String key, int x, int y, int width, int height) {
        super(key, x, y, width, height);
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

        double xOffset = personagem.isAtivo() ? personagem.getX() - getWidth() / (2 * ZOOM_LEVEL) : veiculo.getX() - getWidth() / (2 * ZOOM_LEVEL);
        double yOffset = personagem.isAtivo() ? personagem.getY() - getHeight() / (2 * ZOOM_LEVEL) : veiculo.getY() - getHeight() / (2 * ZOOM_LEVEL);

        g2d.scale(ZOOM_LEVEL, ZOOM_LEVEL);
        g2d.translate(-xOffset, -yOffset);

        paintWithoutZoom(g2d);

        //System.out.println("personagem.getBounds() = " + personagem.getBounds());

        // Desenha as colisões, use apenas para testes
        //showColisionRectangle(g2d); // TODO: Descomentar para mostrar os retângulos de colisão

        // TODO: FPS  -  Da forma que está o fps é exibido apenas ao final da tela
        g2d.setColor(Color.WHITE);
        //System.out.println(GameLoop.frameCount);
        g2d.setFont(BaseFrame.DEFAULT_FONT.deriveFont(Font.BOLD, 20));
        g2d.drawString("FPS: " + GameLoop.frameCount, 20, BaseFrame.DEFAULT_HEIGHT - 20);

        verificarColisao();

        verificarGanhoMissao();

        // Dispose the off-screen graphics
        offscreenGraphics.dispose();

        // Draw the off-screen image to the screen
        g.drawImage(offscreenImage, 0, 0, this);
    }

    public void paintWithoutZoom(Graphics2D g2d) {

        g2d.drawImage(logica.getCamada("floor").camada, 0, 0, null);
        g2d.drawImage(logica.getCamada("colision").camada, 0, 0, null);

        g2d.drawImage(veiculo.getSprites()[veiculo.getAparencia()], veiculo.getX(), veiculo.getY(), null);

        // Desenha o personagem
        if (personagem.isAtivo()) {
            g2d.drawImage(personagem.getSprites()[personagem.getAparencia()], personagem.getX(), personagem.getY(), null);
        }

        g2d.drawImage(logica.getCamada("top").camada, 0, 0, null);

        // Desenha o objetivo da missão atual
        desenharObjetivo(g2d);

    }

    private void showColisionRectangle(Graphics2D g2d) {

        g2d.setColor(Color.RED); // Cor dos retângulos de colisão
        for (Rectangle collisionRect : GameController.colisao) {
            g2d.drawRect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
        }
        g2d.drawRect(personagem.getX() - Personagem.DIFF_COLISAO, personagem.getY() - Personagem.DIFF_COLISAO, personagem.getLarguraPersonagem() + (Personagem.DIFF_COLISAO * 2), personagem.getAlturaPersonagem() + Personagem.DIFF_COLISAO);
        //g2d.drawRect(veiculo.getX() - Veiculo.DIFF_COLISAO, veiculo.getY(), veiculo.getLarguraPersonagem() + (Veiculo.DIFF_COLISAO * 2), veiculo.getAlturaPersonagem() + Veiculo.DIFF_COLISAO);
        g2d.drawRect((int) veiculo.getVeiculoRectangle().getX(), (int) veiculo.getVeiculoRectangle().getY(), (int) veiculo.getVeiculoRectangle().getWidth(), (int) veiculo.getVeiculoRectangle().getHeight());

        g2d.setColor(Color.GREEN); // Cor dos retângulos de interação
        g2d.drawRect((int) personagem.getBounds().getX(), (int) personagem.getBounds().getY(), (int) personagem.getBounds().getWidth(), (int) personagem.getBounds().getHeight());
        g2d.drawRect((int) veiculo.getBounds().getX(), (int) veiculo.getBounds().getY(), (int) veiculo.getBounds().getWidth(), (int) veiculo.getBounds().getHeight());

    }

    public void verificarColisao() {
        if (!personagem.isAtivo() && veiculo.colisao()) {
            if (!isGameOver) {
                isGameOver = true;
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Sinto muito, você colidiu!", "Game Over", JOptionPane.ERROR_MESSAGE);
                    gameController.resetGame();
                    // Redefinir o estado do jogo
                    isGameOver = false;
                });
            }
        }
    }

    public void verificarGanhoMissao() {
        if (veiculo.getBounds().intersects(logica.getFaseAtual().getMissaoAtual().getObjetivo())) {
            if (!isMissaoConcluida) {
                isMissaoConcluida = true;
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Parabéns, você conseguiu. Recebeu 100 moedas pela sua entrega e passou para a próxima missão!", "Parabéns", JOptionPane.INFORMATION_MESSAGE);
                    logica.getFaseAtual().getMissaoAtual().setConcluida(true);
                    personagem.setMoedas(personagem.getMoedas() + logica.getFaseAtual().getMissaoAtual().getRecompensa());
                    logica.getFaseAtual().proximaMissao();
                    gameController.getInventoryPanel().setMissaoAtual(logica.getFaseAtual().getMissaoAtual());
                    gameController.getInventoryPanel().repaint();
                    isMissaoConcluida = false;
                });
            }
        }
    }

    public void desenharObjetivo(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        Fase faseAtual = logica.getFaseAtual();
        Missao missaoAtual = faseAtual.getMissaoAtual();
        g2d.fillRect(missaoAtual.getObjetivo().x, missaoAtual.getObjetivo().y, missaoAtual.getObjetivo().width, missaoAtual.getObjetivo().height);
    }

    private void drawGameOverPanel(Graphics2D g2d, int x, int y) {
        String message = "Game Over";
        Font font = new Font("Arial", Font.BOLD, 30);
//        FontMetrics metrics = g2d.getFontMetrics(font);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(veiculo.getX() - 100, veiculo.getY() - 100, 300, 150);
        g2d.setFont(font);
        g2d.setColor(Color.RED);
        g2d.drawString(message, x, y);
    }

}
