package br.com.ihm.marianacvn.model;

import br.com.ihm.marianacvn.controller.GameController;
import br.com.ihm.marianacvn.view.ComandosPanel;
import br.com.ihm.marianacvn.view.components.GameComandoLabel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Veiculo extends Sprite {
    private int vida;
    private int pontos;
    private boolean ativo;
    public static final int VELOCIDADE = 32;
    public static final int DIFF_COLISAO = -20;
    private List<GameComandoLabel> filaComandos;
    private ComandosPanel comandosPanel;
    private int frame = 0;

    public Veiculo(int aparencia, int largura, int altura, int colunas, int linhas, int x, int y, String endereco) {
        super(aparencia, largura, altura, colunas, linhas, x, y, endereco);
        vida = 100;
        ativo = true;
        filaComandos = new ArrayList<>();
    }

    public void animar(String direcao) {
        switch (direcao) {
            case "cima":
                setAparencia(frame * 4);
                break;
            case "esquerda":
                setAparencia(frame * 4 + 1);
                break;
            case "baixo":
                setAparencia(frame * 4 + 2);
                break;
            case "direita":
                setAparencia(frame * 4 + 3);
                break;
        }
        frame = (frame + 1) % 6; // Reset frame back to 0 after it reaches 5
    }

    @Override
    public void movimento() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (GameComandoLabel comando : filaComandos) {
                    String direcao = comando.getDirecao();
                    switch (direcao) {
                        case "direita": {
                            int xR = getX();
                            int yR = getY();
                            if (xR < 1856 - Veiculo.DIFF_COLISAO) {
                                setX(xR + Veiculo.VELOCIDADE);
                                setY(yR);
                            }
                            break;
                        }
                        case "esquerda": {
                            int xL = getX();
                            int yL = getY();
                            if (yL > Veiculo.DIFF_COLISAO) {
                                setX(xL - Veiculo.VELOCIDADE);
                                setY(yL);
                            }
                            break;
                        }
                        case "cima": {
                            int xU = getX();
                            int yU = getY();
                            if (yU > Veiculo.DIFF_COLISAO) {
                                setX(xU);
                                setY(yU - Veiculo.VELOCIDADE);
                            }
                            break;
                        }
                        case "baixo": {
                            int xD = getX();
                            int yD = getY();
                            if (yD < 1016 - Veiculo.DIFF_COLISAO) {
                                setX(xD);
                                setY(yD + Veiculo.VELOCIDADE);
                            }
                            break;
                        }
                    }
                    animar(direcao);
                    Thread.sleep(500); // Tempo de espera entre os comandos
                }
                return null;
            }

            @Override
            protected void done() {
                List<GameComandoLabel> comandosToRemove = new ArrayList<>(filaComandos);
                for (GameComandoLabel comando : comandosToRemove) {
                    comandosPanel.remove(comando);
                }
                comandosPanel.revalidate();
                comandosPanel.repaint();
                filaComandos.clear();
            }
        };
        worker.execute();
    }

    public boolean colisao(List<Rectangle> tmp, int x, int y) {
        Rectangle veiculo = new Rectangle(
                getX() + x - DIFF_COLISAO,
                getY() + y - DIFF_COLISAO,
                getLarguraPersonagem() + (DIFF_COLISAO * 2),
                getAlturaPersonagem() + DIFF_COLISAO
        );
        for (Rectangle rectangle : tmp) {
            if (rectangle.intersects(veiculo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setX(int posX) {
        if (!colisao(GameController.colisao, posX - getX(), 0))
            super.setX(posX);
    }

    @Override
    public void setY(int posY) {
        if (!colisao(GameController.colisao, 0, posY - getY()))
            super.setY(posY);
    }

}