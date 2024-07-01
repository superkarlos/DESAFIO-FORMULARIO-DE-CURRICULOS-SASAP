package br.com.formulario.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.formulario.DTO.CurriculoFormDto;
import br.com.formulario.services.CurriculoService;
import jakarta.validation.Valid;



@Controller
public class CurriculoController {

    @Autowired
    private CurriculoService curriculoService;

    @GetMapping("/formulario")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        model.addObject("curriculoFormDto", new CurriculoFormDto(null, null, null, null, null, null, null));
        model.setViewName("index.html");
        return model;
    }

    @PostMapping("/formulario")
    public ModelAndView post(@Valid CurriculoFormDto curriculoFormDto,
                             BindingResult result,
                             WebRequest request) {


        ModelAndView model = new ModelAndView();
        if (result.hasErrors()) {
            model.setViewName("index.html");
            return model;
        }
        
        MultipartFile file = curriculoFormDto.getCurriculo();
        if (!curriculoService.isCurriculoValido(file)) {
            result.rejectValue("curriculo", "error.curriculo", "O currículo deve estar no formato .doc, .docx ou .pdf");
            model.setViewName("index.html");
            return model;
        }
        if (file.getSize() > 1_048_576) { // 1MB em bytes
            result.rejectValue("curriculo", "error.curriculo", "O currículo não deve exceder 1MB");
            model.setViewName("index.html");
            return model;
        }

        String ip = request.getContextPath();
        System.out.println(curriculoFormDto);
        model.setViewName("sucesso");
        return model;
    }
}
