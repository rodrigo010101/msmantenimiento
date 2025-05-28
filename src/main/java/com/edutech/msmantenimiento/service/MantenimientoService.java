package com.edutech.msmantenimiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.msmantenimiento.model.Mantenimiento;
import com.edutech.msmantenimiento.respository.MantenimientoRepository;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;
    // crear

    public Mantenimiento save(Mantenimiento crearMantenimiento) {
        return mantenimientoRepository.save(crearMantenimiento);
    }

    // Buscar por id
    public Mantenimiento findById(int idventana) {
        return mantenimientoRepository.findById(idventana);
    }

    // listar
    public List<Mantenimiento> findAll() {
        return mantenimientoRepository.findAll();
    }

    // delete
    public void deleteById(int idventana) {
        mantenimientoRepository.deleteById(idventana);
    }

    public boolean updateMantenimiento(int idventana, Mantenimiento mantenimiento) {

        Mantenimiento mante = mantenimientoRepository.findById(idventana);

        mante.setEstado(mantenimiento.getEstado());
        mante.setFechaFinProgramada(mantenimiento.getFechaFinProgramada());
        mante.setFechaInicioProgramada(mantenimiento.getFechaInicioProgramada());
        mante.setIdventana(idventana);

        mantenimientoRepository.save(mante);
        return true;

    }
}
