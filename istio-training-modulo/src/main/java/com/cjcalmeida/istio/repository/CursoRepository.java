package com.cjcalmeida.istio.repository;

import com.cjcalmeida.istio.entity.CursoEntity;
import com.cjcalmeida.istio.entity.ModuloEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CursoRepository extends CrudRepository<CursoEntity, Integer> {

    CursoEntity findByCursoId(String cursoId);
}
