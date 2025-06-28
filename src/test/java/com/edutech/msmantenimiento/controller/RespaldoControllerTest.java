package com.edutech.msmantenimiento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.msmantenimiento.model.Respaldo;
import com.edutech.msmantenimiento.service.RespaldoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(RespaldoController.class)

public class RespaldoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RespaldoService respaldoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Respaldo respaldo;

    @BeforeEach
    void setUp() {
        // configura un objeto de respaldo
        respaldo = new Respaldo();
        respaldo.setIdrespaldo(1);
        respaldo.setHabilitado(true);
        respaldo.setFechaInicio(LocalDate.now());
    }

    @Test
    public void testGetAllEstudiantes() throws Exception {
        when(respaldoService.findAllList()).thenReturn(List.of(respaldo));
        // Realiza la peticion GET a /api/respaldo y verifica que la respuesta sea
        // correcta

        mockMvc.perform(get("api/v1/respaldo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].habilitado").value(true))
                .andExpect(jsonPath("$[0].fechaInicio").value(LocalDate.now()));
    }

    @Test
    public void testGetRespaldoPorId() throws Exception {
        when(respaldoService.findById(1)).thenReturn(Optional.of(respaldo));

        // REALIZAR LA PETICION GET A /idrespaldo
        mockMvc.perform(get("/idrespaldo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.habilitado").value(false));
    }

    @Test
    public void testCreateEstudiante() throws Exception {

        // definir el comportamiento del mock cuando se llame al metodo save y devueve
        // el obj respaldo
        when(respaldoService.save(respaldo)).thenReturn(respaldo);
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now();

        // objeto de prueba
        // arnandi el json del request. esto es lo que envio al endpoint.
        Respaldo nuevorespaldo = new Respaldo();
        nuevorespaldo.setIdrespaldo(1);
        nuevorespaldo.setFechaInicio(fechaInicio);
        nuevorespaldo.setFechaFin(fechaFin);
        nuevorespaldo.setHabilitado(true);

        // conversion a json usando el ObjectMapper inyectado
        String jsonBody = objectMapper.writeValueAsString(nuevorespaldo);
        // realizar la peticion post a /api/v1/respaldo con e objeto Respaldo en formato
        // json y verificamos la respuesta que el endpoint devuelve
        mockMvc.perform(post("/api/v1/respaldo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)) // convierte ek objeto respaldo a json
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.fechaInicio").value(LocalDate.now()))
                .andExpect(jsonPath("$.fechaFin").value(LocalDate.now()))
                .andExpect(jsonPath("$.habilitado").value(true));
    }

    @Test
    public void testUpdateRespaldo() throws Exception {
        when(respaldoService.save(any(Respaldo.class))).thenReturn(respaldo);

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now();

        // objeto de prueba
        // arnandi el json del request. esto es lo que envio al endpoint.
        Respaldo nuevorespaldo = new Respaldo();
        nuevorespaldo.setIdrespaldo(1);
        nuevorespaldo.setFechaInicio(fechaInicio);
        nuevorespaldo.setFechaFin(fechaFin);
        nuevorespaldo.setHabilitado(true);

        // conversion a json usando el ObjectMapper inyectado
        String jsonBody = objectMapper.writeValueAsString(nuevorespaldo);
        // realizar una peticion PUT a /api/v1/respaldo/1
        mockMvc.perform(put("/api/v1/respaldo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)) // convierte ek objeto respaldo a json
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.fechaInicio").value(LocalDate.now()))
                .andExpect(jsonPath("$.fechaFin").value(LocalDate.now()))
                .andExpect(jsonPath("$.habilitado").value(true));
    }

    @Test
    public void testDeleteRespaldo() throws Exception {

        // definit el comportamiento del mock al llamar el metodo delete
        doNothing().when(respaldoService).deleteById(1);

        // realizar una peticion delete
        mockMvc.perform(delete("/api/v1/respaldo/1"))
                .andExpect(status().isOk()); // verifica la respuesta de estado ok 200

        // verificar que el metodo delete se haya llamado correctamente
        verify(respaldoService, times(1)).deleteById(1);
    }
}
