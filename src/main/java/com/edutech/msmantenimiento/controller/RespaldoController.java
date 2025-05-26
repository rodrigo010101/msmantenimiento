package com.edutech.msmantenimiento.controller;

import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.msmantenimiento.model.Respaldo;
import com.edutech.msmantenimiento.service.RespaldoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RespaldoController {
    @Autowired
    private RespaldoService respaldoService;

    // list
    @GetMapping
    public ResponseEntity<List<Respaldo>> listarRespaldo() {
        // obj
        List<Respaldo> respaldo = respaldoService.findAllList();
        if (respaldo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(respaldo, HttpStatus.OK);
        }
    }

    // read
    @GetMapping("/idrespaldo")
    public ResponseEntity<Respaldo> readRespaldo(@PathVariable Integer idrespaldo) {
        // obj
        Optional<Respaldo> respaldo = respaldoService.findById(idrespaldo);
        if (respaldo.isPresent()) {
            return new ResponseEntity<>(respaldo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete post mapping
    @DeleteMapping("/{idrespaldo}")
    public ResponseEntity<?> deleteRespaldo(@PathVariable Integer idrespaldo) {
        try {
            respaldoService.deleteById(idrespaldo);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // create
    @PostMapping
    public ResponseEntity<Respaldo> crearRespaldo(@RequestBody Respaldo respaldo) {
        // obj
        Optional<Respaldo> crearRespaldo = respaldoService.findById(respaldo.getIdrespaldo());
        if (crearRespaldo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            // nuevoObjeto
            Respaldo nuevoRespaldo = respaldoService.save(respaldo);
            return new ResponseEntity<>(nuevoRespaldo, HttpStatus.OK);
        }
    }

    // update
    @PutMapping("/{idrespaldo}")
    public ResponseEntity<Respaldo> updateRespaldo(@PathVariable Integer idrespaldo, @RequestBody Respaldo respaldo) {
        Optional<Respaldo> respald = respaldoService.findById(idrespaldo);

        if (!respald.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        try {
            Respaldo newrespaldo = respald.get();
            newrespaldo.setFechaInicio(respaldo.getFechaInicio());
            newrespaldo.setFechaFin(respaldo.getFechaFin());
            newrespaldo.setEstado(respaldo.getEstado());
            newrespaldo.setTamano(respaldo.getTamano());
            respaldoService.save(newrespaldo);
            return new ResponseEntity<>(newrespaldo, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}