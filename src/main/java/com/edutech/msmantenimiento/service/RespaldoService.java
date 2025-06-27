package com.edutech.msmantenimiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.msmantenimiento.model.Respaldo;
import com.edutech.msmantenimiento.respository.RespaldoRepository;

@Service
public class RespaldoService {

    @Autowired
    private RespaldoRepository respaldoRepository;

    // crear
    public Respaldo save(Respaldo respaldo) {
        return respaldoRepository.save(respaldo);
    }

    // buscar por id
    public Optional<Respaldo> findById(int idrespaldo) {
        return respaldoRepository.findById(idrespaldo);
    }

    // listar
    public List<Respaldo> findAllList() {
        return respaldoRepository.findAll();
    }

    // delete por id
    public void deleteById(int idrespaldo) {
        respaldoRepository.deleteById(idrespaldo);
    }

    // incorporar el activar y desactuivar respaldo
    // public boolean desactivarRespaldo(){
    // }

}
