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
import br.com.formulario.model.Curriculo;
import br.com.formulario.services.CurriculoService;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class CurriculoController {

    @Autowired
    private CurriculoService curriculoService;
    
    
    @GetMapping("/formulario")
    public String get_formulario(Model model) {
        model.addAttribute("curriculoFormDto", new CurriculoFormDto(null, null, null, null, null, null, null));
        return "index";
    }

    @PostMapping("/formulario")
   public String post_formulario(@RequestParam("file") MultipartFile file,
                             @ModelAttribute("curriculoFormDto") CurriculoFormDto formDTO,
                             BindingResult bindingResult,
                             HttpServletRequest request) throws IOException {

        Curriculo curriculum = new Curriculo();
        BeanUtils.copyProperties(formDTO, curriculum);
        if (bindingResult.hasErrors()) {
            return "index";
        }
        if (file.isEmpty() || !ValidarArquivo(file)) {
            bindingResult.rejectValue("arquivo", "file.invalid", "Por favor, envie um arquivo .doc, .docx ou .pdf.");
            return "index";
        }
        if (file.getSize() > 1048576) { // 1MB
            bindingResult.rejectValue("arquivo", "file.size", "O tamanho máximo do arquivo é 1MB.");
            return "index";
        }

         curriculum.setArquivo(file.getBytes());
         curriculum.setIp(request.getRemoteAddr());
         curriculum.setDataHoraEnvio(LocalDateTime.now());

         curriculoService.save(curriculum);

        return "enviado"; // Redirecionar para página de enviado
    }

    private boolean ValidarArquivo(MultipartFile file) {
        String[] allowedExtensions = {"doc", "docx", "pdf"};
        for (String ext : allowedExtensions) {
            if (file.getOriginalFilename().toLowerCase().endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    }

}
