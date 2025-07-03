package com.edutech.msmantenimiento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.edutech.msmantenimiento.model.Mantenimiento;
import com.edutech.msmantenimiento.model.Mantenimiento.TipoEstado;
import com.edutech.msmantenimiento.service.MantenimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(MantenimientoController.class)

public class MantenimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MantenimientoService mantenimientoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Mantenimiento mantenimiento;

    @BeforeEach
    void setUp() {
        // configurar un objeto de respaldo
        LocalDate fechaIni = LocalDate.now();
        LocalDate fechaFin = LocalDate.now();
        mantenimiento = new Mantenimiento();
        mantenimiento.setIdventana(1);
        mantenimiento.setFechaInicioProgramada(fechaIni);
        mantenimiento.setFechaFinProgramada(fechaFin);
    }

    @Test
    public void testGetAllMantenimiento() throws Exception {
        Mantenimiento mantenimiento = new Mantenimiento();
        LocalDate fechaInicioProgramada = LocalDate.now();
        mantenimiento.setEstado(TipoEstado.COMPLETA);
        mantenimiento.setIdventana(1);
        mantenimiento.setFechaInicioProgramada(fechaInicioProgramada);
        // definir el comportamiento del mock se llama al metodo findAll y retorna el

        when(mantenimientoService.findAll()).thenReturn(List.of(mantenimiento));

        // realizar la peticion Get a la /api/v1/mantenimiento
        mockMvc.perform(get("/api/v1/mantenimiento"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idventana").value(1))
                .andExpect(jsonPath("$[0].estado").value(Matchers.equalTo("COMPLETA")))
                .andExpect(jsonPath("$[0].fechaInicioProgramada").value(fechaInicioProgramada.toString()));
    }

    @Test
    public void tesGetbuscarporId() throws Exception {
        // definir el comportamiento del Mock se llama el metodo findById y retorna el
        // objeto
        when(mantenimientoService.findById(1)).thenReturn(mantenimiento);

        // REALIZAR LA PETICION GET
        mockMvc.perform(get("/api/v1/mantenimiento/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idventana").value(1));
    }

    @Test
    public void testCrearMantenimiento() throws Exception {
        // definir el comportamiento del mock , se llama al metodo crear
        when(mantenimientoService.save(mantenimiento)).thenReturn(mantenimiento);

        // objeto de prueba

        LocalDate fechaInicioProgramada = LocalDate.now();
        LocalDate fechaFinProgramada = LocalDate.now();

        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setIdventana(1);
        mantenimiento.setFechaInicioProgramada(fechaInicioProgramada);
        mantenimiento.setFechaFinProgramada(fechaFinProgramada);

        // conversion a json
        String jsonBody = objectMapper.writeValueAsString(mantenimiento);

        mockMvc.perform(post("/api/v1/mantenimiento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idventana").value(1))
                .andExpect(jsonPath("$.fechaInicioProgramada").value(fechaInicioProgramada.toString()))
                .andExpect(jsonPath("$.fechaFinProgramada").value(fechaFinProgramada.toString()));
    }

    @Test
    public void testUpdateMantenimiento() throws Exception {

        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setIdventana(1);
        // definir comportamiento de mock del metodo update
        when(mantenimientoService.updateMantenimiento(eq(1), any(Mantenimiento.class))).thenReturn(true);

        LocalDate fechaInicioProgramada = LocalDate.now();
        LocalDate fechaFinProgramada = LocalDate.now();
        // objeto de prueba
        Mantenimiento nuevoMantenimiento = new Mantenimiento();
        nuevoMantenimiento.setIdventana(1);
        nuevoMantenimiento.setFechaInicioProgramada(fechaInicioProgramada);
        nuevoMantenimiento.setFechaFinProgramada(fechaFinProgramada);

        String jsonBody = objectMapper.writeValueAsString(nuevoMantenimiento);

        mockMvc.perform(put("/api/v1/mantenimiento/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idventana").value(1))
                .andExpect(jsonPath("$.fechaFinProgramada").value(fechaInicioProgramada.toString()))
                .andExpect(jsonPath("$.fechaFinProgramada").value(fechaFinProgramada.toString()));
    }

    @Test
    public void testDeleteMantenimiento() throws Exception {

        // definir el comportamiento del mock , usando el metodo delete por ID
        doNothing().when(mantenimientoService).deleteById(1);

        mockMvc.perform(delete("/api/v1/mantenimiento/1"))
                .andExpect(status().isOk());
        verify(mantenimientoService, times(1)).deleteById(1);

    }
}
