package com.edutech.msmantenimiento.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/respaldo")

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
    @GetMapping("/{idrespaldo}")
    public ResponseEntity<Respaldo> getRespaldoById(@PathVariable Integer idrespaldo) {
        // obj
        Optional<Respaldo> respaldo = respaldoService.findById(idrespaldo);
        if (respaldo.isPresent()) {
            return respaldo
                    .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        Respaldo nuevoRespaldo = respaldoService.save(respaldo);
        return new ResponseEntity<>(nuevoRespaldo, HttpStatus.OK);
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
            return ResponseEntity.ok(newrespaldo);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}