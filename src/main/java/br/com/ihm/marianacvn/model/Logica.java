package br.com.ihm.marianacvn.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Logica {
	private List<Personagem> personagens;
	private int num1, num2;
	private final List<Camada> camadas;
	private List<Fase> fases;
	private boolean ganhou;

	public Logica() {
		camadas = new ArrayList<>();
		fases = new ArrayList<>();
		camadas.add(new Camada("floor", 60, 40, 32, 32, "/assets/images/tiled-project/grid-tileset.png", "/assets/tiled/floor.txt"));
		camadas.add(new Camada("colision", 60, 40, 32, 32, "/assets/images/tiled-project/grid-tileset.png", "/assets/tiled/colision.txt"));
		camadas.add(new Camada("top", 60, 40, 32, 32, "/assets/images/tiled-project/grid-tileset.png", "/assets/tiled/top.txt"));

		Fase fase1 = new Fase();
		List<String> regras = List.of(
				"- A cada infração cometida leva pontos na carteira de acordo com a gravidade.",
				"- A cada entrega realizada ganhará 10 moedas.",
				"- Se o condutor conseguir 100 moedas será liberado a próxima fase."
		);
		fase1.getMissoes().add(new Missao("Missão 1: Entregador de Pizza", "Olá, seja bem vindo(a) à Transit Village, a nossa primeira missão é entregar as pizzas no objetivo sem infrigir nenhuma lei de trânsito, preste atenção nas placas e sinalizações. Boa sorte, para cada entrega concluída você ganha moedas e ao infrigir uma lei perde moedas.", regras, new Rectangle(100, 100, 64, 64),false, 10));
		fases.add(fase1);
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

	public Camada getCamada(String key) {
		for (Camada camada : camadas) {
			if (camada.getKey().equals(key)) {
				return camada;
			}
		}
		return null;
	}
}
