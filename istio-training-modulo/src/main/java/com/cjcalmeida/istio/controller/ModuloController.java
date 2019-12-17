package com.cjcalmeida.istio.controller;

import com.cjcalmeida.istio.entity.CursoEntity;
import com.cjcalmeida.istio.entity.ModuloEntity;
import com.cjcalmeida.istio.repository.CursoRepository;
import com.cjcalmeida.istio.repository.ModuloRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/modulos")
@AllArgsConstructor
public class ModuloController {

    private CursoRepository repository;

    @GetMapping("/cursos/{cursoId}")
    public ResponseEntity getByCurso(@PathVariable("cursoId") UUID cursoId){
        CursoEntity curso = repository.findByCursoId(cursoId.toString());
        if(curso != null){
            return ResponseEntity.ok(curso.getModulos());
        }
        return ResponseEntity.notFound().build();
    }
}
