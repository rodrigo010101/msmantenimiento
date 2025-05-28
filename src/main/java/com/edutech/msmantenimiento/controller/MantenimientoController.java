package com.edutech.msmantenimiento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.msmantenimiento.model.Mantenimiento;
import com.edutech.msmantenimiento.service.MantenimientoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/mantenimiento")

public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    // listar
    @GetMapping
    public ResponseEntity<List<Mantenimiento>> listarMantenimiento() {
        // objeto
        List<Mantenimiento> mantenimientos = mantenimientoService.findAll();
        if (mantenimientos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mantenimientos, HttpStatus.OK);
    }

    // crear
    @PostMapping
    public ResponseEntity<Mantenimiento> createMantenimiento(@RequestBody Mantenimiento mantenimiento) {
        // objeto
        Mantenimiento crearMantenimiento = mantenimientoService
                .findById(mantenimiento.getIdventana());
        if (crearMantenimiento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            Mantenimiento nuevoMantenimiento = mantenimientoService.save(mantenimiento);
            return new ResponseEntity<>(nuevoMantenimiento, HttpStatus.CREATED);
        }
    }

    // leer
    @GetMapping("/{idventana}")
    public ResponseEntity<Mantenimiento> readMantenimiento(@PathVariable Integer idventana) {
        Mantenimiento mantenimiento = mantenimientoService.findById(idventana);
        if (mantenimiento != null) {
            return new ResponseEntity<>(mantenimiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete por id
    @DeleteMapping("/{idventana}")
    public ResponseEntity<?> deleteMantenimiento(@PathVariable Integer idventana) {
        try {
            mantenimientoService.deleteById(idventana);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // update por id
    @PutMapping("/{idventana}")
    public ResponseEntity<Mantenimiento> updateMantenimiento(@PathVariable Integer idventana,
            @RequestBody Mantenimiento mantenimiento) {

        if (mantenimientoService.updateMantenimiento(idventana, mantenimiento)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
