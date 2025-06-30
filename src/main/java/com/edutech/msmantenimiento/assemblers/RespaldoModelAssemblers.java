package com.edutech.msmantenimiento.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.edutech.msmantenimiento.controller.RespaldoControllerV2;
import com.edutech.msmantenimiento.model.Respaldo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class RespaldoModelAssemblers implements RepresentationModelAssembler<Respaldo, EntityModel<Respaldo>> {

    @Override
    public EntityModel<Respaldo> toModel(Respaldo respaldo) {

        return EntityModel.of(respaldo,
        linkTo(methodOn(RespaldoControllerV2.class).getRespaldoByCodigo(respaldo.getIdrespaldo())).withSelfRel(),
        linkTo(methodOn(RespaldoControllerV2.class).get)
        );

    }

}
