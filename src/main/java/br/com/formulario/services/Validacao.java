package br.com.formulario.services;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import br.com.formulario.DTO.CurriculoFormDto;
import br.com.formulario.model.Curriculo;
import jakarta.servlet.http.HttpServletRequest;


public class Validacao {
     @Autowired
   // private CurriculoService curriculoService;
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
