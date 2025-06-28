package com.edutech.msmantenimiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.msmantenimiento.model.Respaldo;
import com.edutech.msmantenimiento.respository.RespaldoRepository;

import jakarta.persistence.EntityNotFoundException;

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

    public void activarRespaldo(Integer id) {

        Optional<Respaldo> respaldo = respaldoRepository.findById(id);
        if (respaldo.isPresent()) {
            Respaldo res = respaldo.get();
            res.setHabilitado(true);
            respaldoRepository.save(res);
        }
    }

    public void desactivarRespaldo(Integer id) {
        Optional<Respaldo> respaldo = respaldoRepository.findById(id);
        if (respaldo.isPresent()) {
            Respaldo res = respaldo.get();
            res.setHabilitado(false);
            respaldoRepository.save(res);
        } else {
            throw new EntityNotFoundException("No existe id" + id);
        }

    }
}
