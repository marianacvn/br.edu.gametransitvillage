package br.com.ihm.marianacvn.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class Camada {

    private String key;
    public int mapa[][];
    public BufferedImage camada;
    private BufferedImage tileSet;
    private int mapaWidth;
    private int mapaHeight;
    private int tileWidth;
    private int tileHeight;

    public Camada(String key, int mapaWidth, int mapaHeight, int tileWidth, int tileHeight, String img, String arquivo) {
        this.key = key;
        this.mapaWidth = mapaWidth;
        this.mapaHeight = mapaHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        mapa = new int[mapaHeight][mapaWidth];
        mapa = carregaMatriz(mapa, arquivo);
        try {
            tileSet = ImageIO.read(Objects.requireNonNull(Camada.class.getResourceAsStream(img)));
        } catch (IOException e) {
            System.out.println("Erro ao tileSet.\nEncerrando aplicação");
            System.exit(0);
        }
    }

    public int[][] carregaMatriz(int[][] matz, String arquivo) {
        ArrayList<String> linhasMatrizCamada = new ArrayList<String>();
        InputStream is = Camada.class.getResourceAsStream(arquivo);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String linha = "";
        try {

            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
                linhasMatrizCamada.add(linha);
            }
            int j = 0;
            for (int i = 0; i < linhasMatrizCamada.size(); i++) {
                StringTokenizer token = new StringTokenizer(linhasMatrizCamada.get(i), ",");

                while (token.hasMoreElements()) {
                    matz[i][j] = Integer.parseInt(token.nextToken());
                    j++;
                }
                j = 0;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("nao carregou arquivo mapa");
            System.exit(0);
        } catch (IOException ioException) {
            System.out.println("erro na leitura do mapa");
            System.exit(0);
        }
        return matz;
    }

    public void montarMapa() {

        int lar = tileWidth * mapaWidth;
        int alt = tileHeight * mapaHeight;

        camada = new BufferedImage(lar, alt, BufferedImage.TYPE_4BYTE_ABGR);
        camada.createGraphics();

        int tile;
        int tileRow;
        int tileCol;
        int colunasTileSet = tileSet.getWidth() / tileWidth;
//		System.out.println(colunasTileSet);

        for (int i = 0; i < mapaHeight; i++) {
            for (int j = 0; j < mapaWidth; j++) {
                tile = (mapa[i][j] != 0) ? (mapa[i][j] - 1) : 16;
                tileRow = (tile / (colunasTileSet)) | 0;
                tileCol = (tile % (colunasTileSet)) | 0;
                camada.getGraphics().drawImage(tileSet, (j * tileHeight), (i * tileWidth), (j * tileHeight) + tileHeight,
                        (i * tileWidth) + tileWidth, (tileCol * tileHeight), (tileRow * tileWidth),
                        (tileCol * tileHeight) + tileHeight, (tileRow * tileWidth) + tileWidth, null);
            }
        }
    }

    /**
     * @return lista de Rectangle para calculo da colisão
     */
    public List<Rectangle> montarColisao(Personagem veiculo) {
        List<Rectangle> tmp = new ArrayList<Rectangle>();
        for (int i = 0; i < mapaHeight; i++) {
            for (int j = 0; j < mapaWidth; j++) {
                if (mapa[i][j] != 0) {
                    tmp.add(new Rectangle((j * tileHeight), (i * tileWidth), tileWidth, tileHeight));
                }
            }
        }
        // Adiciona o veículo na lista de colisão
        tmp.add(new Rectangle(
                veiculo.getX()-Personagem.DIFF_COLISAO,
                veiculo.getY(),
                veiculo.getLarguraPersonagem()+(Personagem.DIFF_COLISAO*2),
                veiculo.getAlturaPersonagem()+Personagem.DIFF_COLISAO
        ));
        return tmp;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Camada) {
            Camada tmp = (Camada) obj;
            return tmp.getKey().equals(this.getKey());
        }
        return false;
    }

}