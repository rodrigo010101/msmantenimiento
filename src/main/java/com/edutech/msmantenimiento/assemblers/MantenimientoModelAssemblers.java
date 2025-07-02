package com.edutech.msmantenimiento.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.edutech.msmantenimiento.controller.MantenimientoControllerV2;
import com.edutech.msmantenimiento.model.Mantenimiento;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class MantenimientoModelAssemblers
        implements RepresentationModelAssembler<Mantenimiento, EntityModel<Mantenimiento>> {

    @Override

    public @NonNull EntityModel<Mantenimiento> toModel(@NonNull Mantenimiento mantenimiento) {

        return EntityModel.of(mantenimiento,
                linkTo(methodOn(MantenimientoControllerV2.class).getMantenimientoById(mantenimiento.getIdventana()))
                        .withSelfRel(),
                linkTo(methodOn(MantenimientoControllerV2.class).getAllMantenimiento()).withRel("mantenimiento"));
    }

}
