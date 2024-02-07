package br.com.ihm.marianacvn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DadosPersonagem {

    private String nomePersonagem;
    private String cpf;
    private String rg;
    private String dataNascimento;
    private String categoriaHabilitacao;
    private String nroRegistro;
    private String validade;

}
