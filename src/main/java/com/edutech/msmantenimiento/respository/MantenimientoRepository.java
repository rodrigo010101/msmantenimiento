package com.edutech.msmantenimiento.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.edutech.msmantenimiento.model.Mantenimiento;

import io.micrometer.common.lang.NonNull;

import java.util.Optional;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {
    // crear
    // Mantenimiento save(Mantenimiento mantenimiento);

    // buscar por id
    @Override
    @NonNull
    Optional<Mantenimiento> findById(@NonNull Integer idventana);

    // listar
    // List<Mantenimiento> findAll();

    // delete
    // void deleteBy(Integer idventana);
}
