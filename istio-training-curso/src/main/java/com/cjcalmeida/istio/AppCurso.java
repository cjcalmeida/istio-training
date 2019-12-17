package com.cjcalmeida.istio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SpringBootApplication
public class AppCurso {
    public static void main( String[] args ) {
        SpringApplication.run(AppCurso.class, args);
    }

    @RestController
    @RequestMapping("/v1/cursos")
    public class CursoController {

        @GetMapping
        public void list() {

        }

        @GetMapping("/{id}")
        public void get(@PathVariable("id") UUID id) {

        }

        @PostMapping
        public void create() {

        }

    }
}
