package com.edutech.msmantenimiento.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.edutech.msmantenimiento.assemblers.RespaldoModelAssemblers;
import com.edutech.msmantenimiento.model.Respaldo;
import com.edutech.msmantenimiento.service.RespaldoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v2/respaldo")
public class RespaldoControllerV2 {

        @Autowired
        private RespaldoService respaldoService;

        @Autowired
        private RespaldoModelAssemblers assembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        public CollectionModel<EntityModel<Respaldo>> getAllRespaldo() {
                List<EntityModel<Respaldo>> respaldo = respaldoService.findAllList().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(respaldo,
                                linkTo(methodOn(RespaldoControllerV2.class).getAllRespaldo()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        public EntityModel<Respaldo> getRespaldoById(@PathVariable int id) {
                Respaldo respaldo = respaldoService.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "respaldo no encontrado"));
                return assembler.toModel(respaldo);
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        public ResponseEntity<EntityModel<Respaldo>> createRespaldo(@RequestBody Respaldo respaldo) {
                Respaldo newRespaldo = respaldoService.save(respaldo);
                return ResponseEntity
                                .created(linkTo(methodOn(RespaldoControllerV2.class)
                                                .getRespaldoById(newRespaldo.getIdrespaldo()))
                                                .toUri())
                                .body(assembler.toModel(newRespaldo));
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        public ResponseEntity<Respaldo> updateRespaldo(@PathVariable int codigo,
                        @RequestBody Respaldo respaldo) {
                Respaldo actualizado = respaldoService.save(respaldo);
                return ResponseEntity.ok(actualizado);
        }

}