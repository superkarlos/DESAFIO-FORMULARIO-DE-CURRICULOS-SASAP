package br.com.formulario.DTO;

import org.springframework.web.multipart.MultipartFile;

import br.com.formulario.enumtipo.Escolaridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurriculoFormDto {
     private String nome;
    private String email;
    private String telefone;
    private String cargoDesejado;
    private Escolaridade escolaridade;
    private String observacoes;
    private MultipartFile curriculo;
}
