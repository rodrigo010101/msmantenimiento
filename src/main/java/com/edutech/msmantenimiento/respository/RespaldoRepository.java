package com.edutech.msmantenimiento.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.msmantenimiento.model.Respaldo;

import java.util.Optional;

@Repository
public interface RespaldoRepository extends JpaRepository<Respaldo, Integer> {

    // crear
    // Respaldo save(Respaldo respaldo);

    // buscar por id

    Optional<Respaldo> findById(int idrespaldo);

    // buscar todos
    // List<Respaldo> findAllList();

    // delete por idRespaldo
    // void deleteById(Integer idRespaldo);

}