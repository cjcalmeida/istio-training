package com.cjcalmeida.istio;

public class App {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
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
