package com.edutech.msmantenimiento.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.msmantenimiento.model.Respaldo;

import io.micrometer.common.lang.NonNull;

import java.util.Optional;

@Repository
public interface RespaldoRepository extends JpaRepository<Respaldo, Integer> {

    // crear
    // Respaldo save(Respaldo respaldo);

    // buscar por id
    @Override
    @NonNull
    Optional<Respaldo> findById(@NonNull Integer idrespaldo);

    // buscar todos
    // List<Respaldo> findAllList();

    // delete por idRespaldo
    // void deleteById(Integer idRespaldo);

}