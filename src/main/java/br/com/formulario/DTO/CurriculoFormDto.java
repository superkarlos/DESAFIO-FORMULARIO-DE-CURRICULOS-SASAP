package br.com.formulario.DTO;

import org.springframework.web.multipart.MultipartFile;

import br.com.formulario.enumtipo.Escolaridade;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurriculoFormDto {
    @NotBlank(message ="Digite seu nome ")
    @Size(min = 5, max = 20, message = "O nome deve ter entre 5 e 20 caracteres")
    @NotNull(message = "Campo nome não pode ser nulo")
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
