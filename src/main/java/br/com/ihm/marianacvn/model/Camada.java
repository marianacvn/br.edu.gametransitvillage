package br.com.ihm.marianacvn.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Camada{
	public  int mapa[][];
	public  BufferedImage camada;
	private BufferedImage tileSet;
	private int mapaWidth;
	private int mapaHeight;
	private int tileWidth;
	private int tileHeight;

	public Camada(int mapaWidth, int mapaHeight, int tileWidth, int tileHeight, String img, String arquivo) {
		this.mapaWidth=mapaWidth;
		this.mapaHeight=mapaHeight;
		this.tileWidth=tileWidth;
		this.tileHeight=tileHeight;
		mapa = new int[mapaWidth][mapaHeight];
		mapa = carregaMatriz(mapa, arquivo);
		try {
			tileSet = ImageIO.read(Camada.class.getResourceAsStream(img));
		} catch (IOException e) {
			System.out.println("Erro ao tileSet.\nEncerrando aplicação");
			System.exit(0);
		}
	}

	public int[][] carregaMatriz(int[][] matz, String arquivo) {
		ArrayList<String> linhasMatrizCamada = new ArrayList<String>();
		InputStream is = Camada.class.getResourceAsStream(arquivo);
		BufferedReader br = new BufferedReader (new InputStreamReader (is));   
		String linha="";
		try {

			while ((linha = br.readLine()) != null){
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
		}
		catch (IOException ioException) {
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
		int colunasTileSet=tileSet.getWidth()/tileWidth;
//		System.out.println(colunasTileSet);
		
		for (int i = 0; i < mapaWidth; i++) {
			for (int j = 0; j < mapaHeight; j++) {
				tile = (mapa[i][j] != 0) ? (mapa[i][j]-1) : 16;
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
	public List<Rectangle> montarColisao() {
		List<Rectangle> tmp = new ArrayList<Rectangle>();
		for (int i = 0; i < mapaWidth; i++) {
			for (int j = 0; j < mapaHeight; j++) {
				if(mapa[i][j] != 0) {
					tmp.add(new Rectangle( (j * tileHeight), (i * tileWidth), tileWidth-5, tileHeight-5));
				}		
			}
		}
		tmp.add(new Rectangle(0, 0, 513, 1)); // topo
		tmp.add(new Rectangle(0, 512, 513, 1)); // baixo
		tmp.add(new Rectangle(0, 0, 1, 513)); // esquerda
		tmp.add(new Rectangle(513, 0, 1, 513)); // direita
		return tmp;
	}
}