package br.com.formulario.services;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.formulario.DTO.CurriculoFormDto;
import br.com.formulario.model.Curriculo;
import br.com.formulario.repository.CurriculoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class CurriculoService {

    private final Path rootLocation = Paths.get("uploads");

    @Autowired
    private CurriculoRepository curriculoRepository;

    public boolean isCurriculoValido(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            return fileExtension.equals(".doc") || fileExtension.equals(".docx") || fileExtension.equals(".pdf");
        }
        return false;
    }

    public void save(CurriculoFormDto curriculoFormDto, String ip) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(curriculoFormDto.getCurriculo().getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Não é possível salvar o arquivo fora do diretório de upload.");
            }
            try (var inputStream = curriculoFormDto.getCurriculo().getInputStream()) {
                Files.copy(inputStream, destinationFile);
            }

            Curriculo curriculo = new Curriculo(
                curriculoFormDto.getNome(),
                curriculoFormDto.getEmail(),
                curriculoFormDto.getTelefone(),
                curriculoFormDto.getCargoDesejado(),
                curriculoFormDto.getEscolaridade(),
                curriculoFormDto.getObservacoes(),
                destinationFile.toString(),
                ip,
                LocalDateTime.now()
            );

            curriculoRepository.save(curriculo);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o currículo", e);
        }
    }
}
