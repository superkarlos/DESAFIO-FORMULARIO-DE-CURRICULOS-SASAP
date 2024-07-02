package br.com.formulario.DTO;

import org.springframework.web.multipart.MultipartFile;

import br.com.formulario.enumtipo.Escolaridade;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurriculoFormDto {
    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String telefone;
    @NotBlank
    @NotNull
    private String cargoDesejado;
    @Enumerated(EnumType.STRING)
    private Escolaridade escolaridade;
    private String observacoes;
    @Lob
    private byte[] arquivo;
}
