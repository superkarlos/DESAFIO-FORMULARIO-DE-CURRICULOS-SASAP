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
import br.com.formulario.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CurriculoController {
    private static final Logger logger = LoggerFactory.getLogger(CurriculoController.class);

    @Autowired
    private CurriculoService curriculoService;
    @Autowired
    private EmailService emailService;
    
    
    @GetMapping("/formulario")
    public String get_formulario(Model model) {
        model.addAttribute("curriculoFormDto", new CurriculoFormDto(null, null, null, null, null, null, null));
        return "index";
    }

    @PostMapping("/formulario")
    public String postFormulario(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("curriculoFormDto") CurriculoFormDto formDTO,
            BindingResult bindingResult,
            HttpServletRequest request) throws IOException {
    
        Curriculo curriculum = new Curriculo();
        BeanUtils.copyProperties(formDTO, curriculum);
        if (bindingResult.hasErrors()) {
            return "index";
        }
        if (file.isEmpty() || !validarArquivo(file)) {
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
    
        EmailDto emailDto = new EmailDto();
        emailDto.setReferencia_ip(request.getRemoteAddr());
        emailDto.setEnviador("jcarlos.alegria2015@gmail.com");
        emailDto.setReceptor(curriculum.getEmail());
        emailDto.setSubjt_titutlo("envio de currículo");
        emailDto.setTexto("Seu currículo foi recebido.");
    
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
    
        curriculoService.save(curriculum);
    
        // Enviar e-mail com anexo
        emailService.enviar(emailModel, curriculum.getArquivo(), "curriculo.pdf");
    
        return "enviado"; // Redirecionar para página de enviado
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
