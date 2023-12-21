package br.com.ihm.marianacvn.model;

import br.com.ihm.marianacvn.controller.GameController;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Personagem extends Sprite {
    private int vida;
    private int pontos;

    public static final int VELOCIDADE = 5;

    public static final int DIFF_COLISAO = -20;

    public Personagem(int aparencia, int largura, int altura, int colunas, int linhas, int x, int y, String endereco) {
        super(aparencia, largura, altura, colunas, linhas, x, y, endereco);
        vida = 100;
    }

    @Override
    public void animar(String direcao) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getSprites()[getAparencia()], getX(), getY(), null);
    }

    @Override
    public void mover(String direcao) {
    }

    public void perderVida() {
        if (colisao()) {
            vida = vida - 1;
        }
    }

//    public boolean colisaoResultado() {
//        Rectangle personagem = new Rectangle(getX()+10, getY()+10,
//                getLarguraPersonagem()-10, getAlturaPersonagem()-10);
//        Rectangle inimigoResultado = new Rectangle(resultado.getX(), resultado.getY(),
//                resultado.getLargura(), resultado.getAltura());
//        if(personagem.intersects(inimigoResultado))
//            return true;
//        return false;
//    }

    public boolean colisao() {
        Rectangle personagem = new Rectangle(getX()+10, getY()+10,
                getLarguraPersonagem()-10, getAlturaPersonagem()-10);
        List<Rectangle> tmp = new ArrayList<Rectangle>();
//        for (Inimigo enemy : inimigo) {
//            tmp.add(new Rectangle(enemy.getX(), enemy.getY(), enemy.getLargura(), enemy.getAltura()));
//        }
        for(Rectangle rectangle : tmp) {
            if(rectangle.intersects(personagem)){
                return true;
            }
        }
        return false;
    }

    public boolean colisao(List<Rectangle> tmp, int x,int y) {
        Rectangle personagem = new Rectangle(getX()+x-DIFF_COLISAO, getY()+y-DIFF_COLISAO,
                getLarguraPersonagem()+(DIFF_COLISAO*2), getAlturaPersonagem()+DIFF_COLISAO);
        for(Rectangle rectangle : tmp) {
            if(rectangle.intersects(personagem)){
                return true;
            }
        }
        return false;
    }

    public String getStatus(int win, int lose) {
        if (vida == lose) {
            return "Game Over!";
        }
        if (pontos == win) {
            return "Ganhou!!!";
        }
        return "";

    }

    public void reset(int posX, int posY) {
        setLocale(posX, posY);
        vida = 100;
        pontos = 0;
    }

    @Override
    public void setX(int posX) {
        if(!colisao(GameController.colisao, posX-getX(), 0))
            super.setX(posX);

    }

    @Override
    public void setY(int posY) {
        if(!colisao(GameController.colisao, 0, posY-getY()))
            super.setY(posY);
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

}
