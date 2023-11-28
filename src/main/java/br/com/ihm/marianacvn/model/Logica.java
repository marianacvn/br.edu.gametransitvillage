package br.com.ihm.marianacvn.model;

import java.util.ArrayList;
import java.util.List;

public class Logica {
	private List<Personagem> personagens;
	private int num1, num2;
	private List<Camada> camadas;
	private boolean ganhou;

	public Logica() {
		camadas = new ArrayList<Camada>();
		camadas.add(new Camada("floor", 60, 40, 32, 32, "/assets/images/tiled-project/grid-tileset.png", "/assets/tiled/floor.txt"));
		camadas.add(new Camada("colision", 60, 40, 32, 32, "/assets/images/tiled-project/grid-tileset.png", "/assets/tiled/colision.txt"));
		camadas.add(new Camada("top", 60, 40, 32, 32, "/assets/images/tiled-project/grid-tileset.png", "/assets/tiled/top.txt"));

//		camadaFundo = Camadas.fase1()[0];
//		camadaColisao = Camadas.fase1()[1];
//		camadaTopo = Camadas.fase1()[2];

	}

	public void iniciarFase() {
//        if (personagens.get(0).getVida() == 0) {
//            personagens.get(0).setInimigo(resetarPosicaoInimigos());
//            camadaFundo = Camadas.fase1()[0];
//            camadaColisao = Camadas.fase1()[1];
//            camadaTopo = Camadas.fase1()[2];
//        }
//        if (personagens.get(0).colisaoResultado()) {
//            int pos = NumerosAleatorios.gerarNumeroAleatorio(posicoes.size());
//            resultado.setX(posicoes.get(pos).x);
//            resultado.setY(posicoes.get(pos).y);
//            personagens.get(0).setPontos(personagens.get(0).getPontos() + 1);
//
//            gerarAparenciaInimigo();
//
//            resultado.setAparencia("" + resultadoOperacao);
//            if (personagens.get(0).getPontos() == 15) {
//                personagens.get(0).setX(92);
//                personagens.get(0).setY(182);
//                personagens.get(1).setX(92);
//                personagens.get(1).setY(182);
//                resultado.setX(448);
//                resultado.setY(160);
//                for (Inimigo enemy : personagens.get(0).getInimigo()) {
//                    enemy.setX(448);
//                    enemy.setY(160);
//                }
//                camadaFundo = Camadas.fase2()[0];
//                camadaColisao = Camadas.fase2()[1];
//                camadaTopo = Camadas.fase2()[2];
//            } else if (personagens.get(0).getPontos() == 30) {
//                ganhou = true;
//            }
//        }
//        if (personagens.get(1).colisaoResultado()) {
//            int pos = NumerosAleatorios.gerarNumeroAleatorio(posicoes.size());
//            resultado.setX(posicoes.get(pos).x);
//            resultado.setY(posicoes.get(pos).y);
//            personagens.get(1).setPontos(personagens.get(1).getPontos() + 1);
//            gerarAparenciaInimigo();
//
//            resultado.setAparencia("" + resultadoOperacao);
//            if (personagens.get(1).getPontos() == 15) {
//                personagens.get(1).setX(92);
//                personagens.get(1).setY(182);
//                personagens.get(0).setX(92);
//                personagens.get(0).setY(182);
//                resultado.setX(448);
//                resultado.setY(160);
//                for (Inimigo enemy : personagens.get(1).getInimigo()) {
//                    enemy.setX(448);
//                    enemy.setY(160);
//                }
//                camadaFundo = Camadas.fase2()[0];
//                camadaColisao = Camadas.fase2()[1];
//                camadaTopo = Camadas.fase2()[2];
//            } else if (personagens.get(1).getPontos() == 30) {
//                ganhou = true;
//            }
//
//        }
	}

	private class Posicao {
		int x;
		int y;

		public Posicao(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public boolean isGanhou() {
		return ganhou;
	}

	public List<Camada> getCamadas() {
		return camadas;
	}

	public Camada getCamada(String key) {
		for (Camada camada : camadas) {
			if (camada.getKey().equals(key)) {
				return camada;
			}
		}
		return null;
	}
}
