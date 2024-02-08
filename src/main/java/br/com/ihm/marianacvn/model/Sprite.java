package br.com.ihm.marianacvn.model;


import br.com.ihm.marianacvn.utils.ErrorHandler;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * Classe responsavel por recortar a imagem,
 * e deixar sua sprite pronta para ser utilizada
 */
@Getter
@Setter
public abstract class Sprite {

    /**
     * Largura e altura somente de uma imagem da sprite
     */
    private int larguraPersonagem, alturaPersonagem;

    /**
     * Imagem de toda a sua Sprite
     */
    protected BufferedImage personagem;
    /**
     * largura e altura total de sua imagem personagem
     */
    protected int largura, altura;
    /**
     * Corresponde a quantidade de linhas e colunas de sua imagem personagem
     */
    protected int linhas, colunas;
    /**
     * Localização x e y da tela
     */
    private int x, y;
    /**
     * Array de todos as imagens de sua Sprite
     * Cada valor é um estado(aparencia) diferente da Sprite
     */
    private BufferedImage[] sprites;
    /**
     * Aparencia atual de sua Sprite.
     * Utilizada para saber qual valor do array das sprites usar
     */
    private int aparencia;

    private int posicaoInicialX;
    private int posicaoInicialY;

    /**
     * @param aparencia - aparencia atual da Sprite
     * @param largura   - largura total da imagem
     * @param altura    - altura total da imagem
     * @param colunas   - quantidade de colunas da imagem
     * @param linhas    - quantidade de linhas da imagem
     * @param x         - localização x da tela
     * @param y         - localização y da tela
     * @param endereco  - endereço da imagem
     */
    protected Sprite(int aparencia, int largura, int altura, int colunas, int linhas, int x, int y, String endereco) {

        try {

            this.personagem = ImageIO.read(Objects.requireNonNull(Sprite.class.getResourceAsStream(endereco)));
            this.aparencia = aparencia;
            this.largura = largura;
            this.altura = altura;

            this.linhas = colunas;
            this.colunas = linhas;
            this.x = x;
            this.y = y;
            this.posicaoInicialX = x;
            this.posicaoInicialY = y;

            sprites = new BufferedImage[colunas * linhas];

            /*
             * Recorta sua imagem em varias,
             * cada recorte significa um estado da Sprite
             *
             */
            for (int i = 0; i < colunas; i++) {
                for (int j = 0; j < linhas; j++) {
//                    sprites[(i * linhas) + j] = personagem.getSubimage(i * (largura/colunas),
//                            j * (altura/linhas), largura/colunas, altura/linhas);
                    sprites[(i * linhas) + j] = personagem.getSubimage(i * largura, j * altura, largura, altura);
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel carregar a Sprite");
            ErrorHandler.logAndExit(e);
        }

        larguraPersonagem = sprites[0].getWidth();
        alturaPersonagem = sprites[0].getHeight();
    }

    /**
     * Metodo abstrato reponsavel por definir como sera o movimento da Sprite
     */
    public abstract void movimento();

    /*
     * Metodos Getters e Setters
     */
    public void setLocale(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void resetLocale() {
        this.x = posicaoInicialX;
        this.y = posicaoInicialY;
        this.aparencia = 0;
    }

    /**
     * @return Rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(x + 5, y + 5, larguraPersonagem - 10, alturaPersonagem - 10);
    }
}