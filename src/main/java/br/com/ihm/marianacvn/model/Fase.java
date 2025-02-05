package br.com.ihm.marianacvn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fase {

    private List<Missao> missoes = new ArrayList<>();
    private Missao missaoAtual;

    public void proximaMissao() {
        if (missoes.indexOf(missaoAtual) < missoes.size() - 1) {
            missaoAtual = missoes.get(missoes.indexOf(missaoAtual) + 1);
        } else {
            System.exit(0);
        }
    }

}
