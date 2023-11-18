package br.com.ihm.marianacvn.model;

import br.com.ihm.marianacvn.controller.ControlePintura;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Personagem extends Sprite {
    private Inimigo[] inimigo;
    private Inimigo resultado;
    private int vida;
    private int pontos;

    public Personagem(Inimigo[] inimigo, Inimigo resultado, int aparencia, int largura, int altura, int colunas, int linhas, int x, int y, String endereco) {
        super(aparencia, largura, altura, colunas, linhas, x, y, endereco);
        this.inimigo = inimigo;
        this.resultado = resultado;
        vida = 100;
    }

    @Override
    public void animar(String direcao) {
        setAparencia(getAparencia() + 1);

        if (direcao.equalsIgnoreCase("cima")) {
            if(getAparencia() < 9 || getAparencia() > 11)
                setAparencia(9);
        } else if (direcao.equalsIgnoreCase("baixo")) {
            if(getAparencia() < 0 || getAparencia() > 2)
                setAparencia(0);
        } else if (direcao.equalsIgnoreCase("esquerda")) {
            if(getAparencia() < 3 || getAparencia() > 5)
                setAparencia(3);
        } else if (direcao.equalsIgnoreCase("direita")) {
            if(getAparencia() < 6 || getAparencia() > 8)
                setAparencia(6);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getSprites()[getAparencia()], getX(), getY(), null);
    }

    @Override
    public void mover(String direcao) {

        switch (direcao) {
            case "cima":
                setY(getY() - 5);
                break;

            case "baixo":
                setY(getY() + 5);
                break;
            case "esquerda":
                setX(getX() - 5);
                break;

            case "direita":
                setX(getX() + 5);
                break;
        }

        if (!direcao.equals(""))
            animar(direcao);
    }

    public void perderVida() {
        if (colisao()) {
            vida = vida - 1;
        }
    }

    public boolean colisaoResultado() {
        Rectangle personagem = new Rectangle(getX()+10, getY()+10,
                getLarguraPersonagem()-10, getAlturaPersonagem()-10);
        Rectangle inimigoResultado = new Rectangle(resultado.getX(), resultado.getY(),
                resultado.getLargura(), resultado.getAltura());
        if(personagem.intersects(inimigoResultado))
            return true;
        return false;
    }

    public boolean colisao() {
        Rectangle personagem = new Rectangle(getX()+10, getY()+10,
                getLarguraPersonagem()-10, getAlturaPersonagem()-10);
        List<Rectangle> tmp = new ArrayList<Rectangle>();
        for (Inimigo enemy : inimigo) {
            tmp.add(new Rectangle(enemy.getX(), enemy.getY(), enemy.getLargura(), enemy.getAltura()));
        }
        for(Rectangle rectangle : tmp) {
            if(rectangle.intersects(personagem)){
                return true;
            }
        }
        return false;
    }

    public boolean colisao(List<Rectangle> tmp, int x,int y) {
        Rectangle personagem = new Rectangle(getX()+x+10, getY()+y+10,
                getLarguraPersonagem()-10, getAlturaPersonagem()-10);
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
    public void setX(int x) {
        if(!colisao(ControlePintura.colisao, x-getX(), 0) && !colisao())
            super.setX(x);

    }

    @Override
    public void setY(int y) {
        if(!colisao(ControlePintura.colisao, 0, y-getY()) && !colisao())
            super.setY(y);
    }



    public Inimigo[] getInimigo() {
        return inimigo;
    }

    public Inimigo getResultado() {
        return resultado;
    }

    public void setInimigo(Inimigo[] inimigo) {
        this.inimigo = inimigo;
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
