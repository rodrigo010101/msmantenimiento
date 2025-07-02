package com.edutech.msmantenimiento.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.msmantenimiento.assemblers.MantenimientoModelAssemblers;
import com.edutech.msmantenimiento.model.Mantenimiento;
import com.edutech.msmantenimiento.service.MantenimientoService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/mantenimiento")
public class MantenimientoControllerV2 {

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private MantenimientoModelAssemblers assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Mantenimiento>> getAllMantenimiento() {
        List<EntityModel<Mantenimiento>> mantenimiento = mantenimientoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(mantenimiento,
                linkTo(methodOn(MantenimientoControllerV2.class).getAllMantenimiento()).withSelfRel());
    }

    // get obtener por id un mantenimiento
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Mantenimiento> getMantenimientoById(@PathVariable int id) {
        Mantenimiento mantenimiento = mantenimientoService.findById(id);

        return assembler.toModel(mantenimiento);

    }

    // post crear
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> crearMantenimiento(@RequestBody Mantenimiento mantenimiento) {
        Mantenimiento newmantenimiento = mantenimientoService.save(mantenimiento);
        return ResponseEntity
                .created(linkTo(methodOn(MantenimientoControllerV2.class)
                        .getMantenimientoById(newmantenimiento.getIdventana()))
                        .toUri())
                .body(assembler.toModel(newmantenimiento));
    }

    // pot actualizar
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> updateMantenimiento(@PathVariable int id,
            @RequestBody Mantenimiento mantenimiento) {
        mantenimiento.setIdventana(id);
        Mantenimiento updMantenimiento = mantenimientoService.save(mantenimiento);
        return ResponseEntity.ok(assembler.toModel(updMantenimiento));

    }

}
