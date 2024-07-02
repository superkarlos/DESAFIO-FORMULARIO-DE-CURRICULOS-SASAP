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
import java.util.List;
import java.util.Optional;

@Service
public class CurriculoService {
    @Autowired
    private CurriculoRepository curriculumRepository;

    public void save(Curriculo curriculum) {
        curriculumRepository.save(curriculum);
    }
    public List<Curriculo> getAllCurriculos() {
        return curriculumRepository.findAll();
    }

    public Optional<Curriculo> getCurriculumById(Long id){
        return curriculumRepository.findById(id);

    }
   
    
}
