package br.com.formulario.controller;



import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;


import br.com.formulario.DTO.CurriculoFormDto;
import br.com.formulario.DTO.EmailDto;
import br.com.formulario.erros.FormExecption;
import br.com.formulario.model.Curriculo;
import br.com.formulario.model.EmailModel;
import br.com.formulario.services.CurriculoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CurriculoController {

    private static final Logger logger = LoggerFactory.getLogger(CurriculoController.class);

    @Autowired
    private CurriculoService curriculoService;
   
    @GetMapping("/formulario")
    public String get_formulario(Model model) {
        model.addAttribute("curriculoFormDto", new CurriculoFormDto(null, null, null, null, null, null, null));
        return "index";
    }

    @PostMapping("/formulario")
    public String postFormulario(@Valid
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("curriculoFormDto") CurriculoFormDto formDTO,
            BindingResult bindingResult,
            HttpServletRequest request)  {
                if (bindingResult.hasErrors()) {
                    System.out.println("erooo");
                    return "index";
                }
                if (file.isEmpty() || !validarArquivo(file)) {
                    bindingResult.rejectValue("arquivo", "file.invalid", "Por favor, envie um arquivo .doc, .docx ou .pdf back.");
                    return "index";
                }
                if (file.getSize() > 1048576) { // 1MB
                    bindingResult.rejectValue("arquivo", "file.size", "O tamanho máximo do arquivo é 1MB back.");
                    return "index";
                }

                Curriculo curriculum = new Curriculo();
                BeanUtils.copyProperties(formDTO, curriculum);
                
                try {
                    curriculum.setArquivo(file.getBytes());
                } catch (IOException e) {
                    
                    logger.error("Erro ao ler o arquivo enviado: " + e.getMessage());
                    return "index";
                }
                
             
                curriculum.setIp(request.getRemoteAddr());
                curriculum.setDataHoraEnvio(LocalDateTime.now());

                String originalFilename = file.getOriginalFilename();
                curriculum.setFileName(originalFilename);
            
                this.curriculoService.save(curriculum);
                return "enviado"; 
            }
            
            private boolean validarArquivo(MultipartFile arquivo) {
                String[] tipo = {"doc", "docx", "pdf"};
                for (String ext : tipo) {
                    if (arquivo.getOriginalFilename().toLowerCase().endsWith("." + ext)) {
                        return true;
                    }
                }
                return false;   
              
            }
               
              
          
    
}
