package br.com.formulario.model;



import java.time.LocalDateTime;

import br.com.formulario.enumtipo.Escolaridade;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor

public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cargoDesejado;
    private Escolaridade escolaridade;
    private String observacoes;
    private String caminhoArquivo;
    private String ip;
    private LocalDateTime dataHoraEnvio;

    // Construtores, getters e setters

    public Curriculo() {}

    public Curriculo(String nome, String email, String telefone, String cargoDesejado, Escolaridade escolaridade, String observacoes, String caminhoArquivo, String ip, LocalDateTime dataHoraEnvio) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cargoDesejado = cargoDesejado;
        this.escolaridade = escolaridade;
        this.observacoes = observacoes;
        this.caminhoArquivo = caminhoArquivo;
        this.ip = ip;
        this.dataHoraEnvio = dataHoraEnvio;
    }

    // Getters e setters
}
