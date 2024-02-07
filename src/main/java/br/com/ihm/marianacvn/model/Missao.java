package br.com.ihm.marianacvn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Missao {
    private String titulo;
    private String descricao;
    private List<String> regras;
    private Rectangle objetivo;
    private boolean concluida;
    private int recompensa;
}
