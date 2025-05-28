package com.edutech.msmantenimiento.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.edutech.msmantenimiento.model.Mantenimiento;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {

    Mantenimiento findById(int idventana);

}
