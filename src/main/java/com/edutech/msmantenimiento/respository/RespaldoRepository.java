package com.edutech.msmantenimiento.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.msmantenimiento.model.Respaldo;

import java.util.Optional;

@Repository
public interface RespaldoRepository extends JpaRepository<Respaldo, Integer> {
    Optional<Respaldo> findById(int idrespaldo);
}