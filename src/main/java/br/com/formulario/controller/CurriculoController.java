package br.com.formulario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CurriculoController {

    @GetMapping
    public String main(){
        return "olaa";
    }
    
}
