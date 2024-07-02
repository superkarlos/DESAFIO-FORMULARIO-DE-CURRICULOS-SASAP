package br.com.formulario.services;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.formulario.model.Curriculo;
import br.com.formulario.repository.CurriculoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CurriculoService {
    @Autowired
    private CurriculoRepository curriculumRepository;

    @Autowired
    private EmailService emailService;

    public void save(Curriculo curriculum) {

        emailService.enviar( curriculum.getArquivo(), "curriculo.pdf", curriculum);
        
        curriculumRepository.save(curriculum);
    }
    public List<Curriculo> getAllCurriculos() {
        return curriculumRepository.findAll();
    }

    public Optional<Curriculo> getCurriculumById(Long id){
        return curriculumRepository.findById(id);

    }
   
    
}
