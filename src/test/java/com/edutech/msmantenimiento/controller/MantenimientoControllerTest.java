package com.edutech.msmantenimiento.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.edutech.msmantenimiento.model.Mantenimiento;
import com.edutech.msmantenimiento.model.Respaldo.enumEstado;
import com.edutech.msmantenimiento.service.MantenimientoService;
import com.edutech.msmantenimiento.service.RespaldoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(RespaldoService.class)

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
        // definir el comportamiento del mock se llama al metodo findAll y retorna el
        // objeto de la lista
        when(mantenimientoService.findAll()).thenReturn(List.of(mantenimiento));

        LocalDate fechaInicio = LocalDate.now();
        // realizar la peticion Get a la /api/v1/mantenimiento
        mockMvc.perform(get("/api/v1/mantenimiento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idventana").value(1))
                .andExpect(jsonPath("$[0].estado").value(enumEstado.COMPLETA))
                .andExpect(jsonPath("$[0].fechaInicio").value(fechaInicio.toString()));
    }

    @Test
    public void tesGetbuscarporId() throws Exception {
        // definir el comportamiento del Mock se llama el metodo findById y retorna el
        // objeto
        when(mantenimientoService.findById(1)).thenReturn(mantenimiento);

        // REALIZAR LA PETICION GET
        mockMvc.perform(get("/api/v1/mantenimiento/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idventana").value(1));
    }

    @Test
    public void testCrearMantenimiento() throws Exception {
        // definir el comportamiento del mock , se llama al metodo crear
        when(mantenimientoService.save(mantenimiento)).thenReturn(mantenimiento);

        // objeto de prueba

        LocalDate fechaInicioPrograma = LocalDate.now();
        LocalDate fechaFinPrograma = LocalDate.now();

        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setIdventana(1);
        mantenimiento.setFechaInicioProgramada(fechaInicioPrograma);
        mantenimiento.setFechaFinProgramada(fechaFinPrograma);

        // conversion a json
        String jsonBody = objectMapper.writeValueAsString(mantenimiento);

        mockMvc.perform(post("/api/v1/mantenimiento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].idVentana").value(1))
                .andExpect(jsonPath("$[0].fechaIncioPrograma").value(fechaInicioPrograma.toString()))
                .andExpect(jsonPath("$[0].fechaFinPrograma").value(fechaFinPrograma.toString()));
    }

    @Test
    public void testUpdateMantenimiento() throws Exception {
        // definir comportamiento de mock del metodo update
        when(mantenimientoService.updateMantenimiento(1, mantenimiento)).thenReturn(true);

        LocalDate fechaIni = LocalDate.now();
        LocalDate fechaFin = LocalDate.now();
        // objeto de prueba
        Mantenimiento nuevoMantenimiento = new Mantenimiento();
        nuevoMantenimiento.setIdventana(1);
        nuevoMantenimiento.setFechaInicioProgramada(fechaIni);
        nuevoMantenimiento.setFechaFinProgramada(fechaFin);

        String jsonBody = objectMapper.writeValueAsString(nuevoMantenimiento);

        mockMvc.perform(put("/api/v1/mantenimiento/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idventena").value(1))
                .andExpect(jsonPath("$[0].fechaInicio").value(fechaIni.toString()))
                .andExpect(jsonPath("$[0].fechaFin").value(fechaFin.toString()));
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
