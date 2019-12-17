package com.cjcalmeida.istio;


import com.cjcalmeida.istio.entity.CursoEntity;
import com.cjcalmeida.istio.entity.ModuloEntity;
import com.cjcalmeida.istio.repository.CursoRepository;
import com.cjcalmeida.istio.repository.ModuloRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

@SpringBootApplication
@Configuration
public class AppModulo extends WebSecurityConfigurerAdapter {
    public static void main( String[] args ) {
        SpringApplication.run(AppModulo.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .oauth2ResourceServer().jwt();
    }

    @Bean
    public ApplicationRunner initDb(ModuloRepository moduloRepository, CursoRepository cursoRepository) {
        return args -> {
            List<ModuloEntity> modulos = new ArrayList<>(3);
            for(String moduloName : Arrays.asList("LOGICA DE PROGRAMAÇÃO", "MATEMATICA I", "MATEMATICA II")) {
                ModuloEntity entity = new ModuloEntity();
                entity.setModuloId(UUID.randomUUID().toString());
                entity.setName(moduloName);
                entity = moduloRepository.save(entity);
                modulos.add(entity);

                CursoEntity curso = new CursoEntity();
                curso.setCursoId(UUID.randomUUID().toString());
                curso.setModulos(modulos);
                cursoRepository.save(curso);
            }
        };
    }
}
