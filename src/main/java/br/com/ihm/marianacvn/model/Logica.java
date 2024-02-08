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
    private Fase faseAtual;
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
                "- A cada entrega realizada ganhará 100 moedas.",
                "- Se o condutor conseguir 300 moedas será liberado a próxima fase."
        );
        fase1.getMissoes().add(
                new Missao(
                        "Missão 1: Entregador de Pizza",
                        "Olá, seja bem vindo(a) à Transit Village, a nossa primeira missão é entregar as pizzas em frente a casa que está marcada como verde no minimapa acima. Para realizar a entrega é preciso estar no veículo, para isto basta apertar ENTER ao lado do veículo, onde será acionado o painel de comandos. Utilize quantos comandos forem necessários e realize a entrega sem infrigir nenhuma lei de trânsito, preste atenção nas placas e sinalizações. Boa sorte, para cada entrega concluída você ganha moedas e ao infrigir uma lei perde moedas.",
                        regras,
                        new Rectangle(855, 225, 64, 64),
                        false,
                        100
                )
        );

        fase1.getMissoes().add(
                new Missao(
                        "Missão 2: Entregador de Pizza",
                        "Parabéns pela sua primeira conquista, o prefeito tem visto o seu esforço e solicitou uma entrega para a casa dele,  a recompensa é mesma, mas o reconhecimento é maio hahar. Boa sorte!",
                        regras,
                        new Rectangle(1585, 535, 64, 64),
                        false,
                        100
                )
        );

        fase1.getMissoes().add(
                new Missao(
                        "Missão 3: Entregador de Pizza",
                        "Nossa que incrível você está indo muito bem! Com isto você conseguiu ser promovido e passou para a pŕoxima fase!",
                        regras,
                        new Rectangle(1810, 920, 64, 64),
                        false,
                        100
                )
        );

        faseAtual = fase1;
        faseAtual.setMissaoAtual(faseAtual.getMissoes().get(0));

        fases.add(fase1);

    }

    public void proximaFase() {
        if (fases.indexOf(faseAtual) < fases.size() - 1) {
            faseAtual = fases.get(fases.indexOf(faseAtual) + 1);
        }
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
