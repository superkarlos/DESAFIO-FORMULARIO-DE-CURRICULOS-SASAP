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
    private TesteEmail testeEmail;

    public void save(Curriculo curriculum) {
       curriculumRepository.save(curriculum);
        System.out.println(curriculum.getId());
        System.out.println("eviando..");
       testeEmail.enviar(curriculum);
      System.out.println("nome "+curriculum.getNome());
     
    }

    
    public List<Curriculo> getAllCurriculos() {
        return curriculumRepository.findAll();
    }

    public Optional<Curriculo> getCurriculumById(Long id){
        return curriculumRepository.findById(id);

    }
   
    
}
