package com.edutech.msmantenimiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.msmantenimiento.model.Mantenimiento;
import com.edutech.msmantenimiento.model.Respaldo;
import com.edutech.msmantenimiento.model.Mantenimiento.TipoEstado;
import com.edutech.msmantenimiento.respository.MantenimientoRepository;

@ExtendWith(MockitoExtension.class)
public class MantenimientoServiceTest {
    @Captor
    private ArgumentCaptor<Mantenimiento> manteCaptor;

    @Mock
    private MantenimientoRepository mantenimientoRepository;

    @InjectMocks
    private MantenimientoService mantenimientoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarMantenimientoYretornarMantenimiento() {
        LocalDate fechaInicioProgramada = LocalDate.now();
        LocalDate fechaFinProgramada = LocalDate.now();
        Respaldo respaldo = new Respaldo();

        Mantenimiento mante = new Mantenimiento(1, fechaInicioProgramada, fechaFinProgramada, TipoEstado.COMPLETA,
                true, respaldo);

        when(mantenimientoRepository.save(mante)).thenReturn(mante);

        Mantenimiento resultado = mantenimientoService.save(mante);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdventana());
        assertEquals(TipoEstado.COMPLETA, resultado.getEstado());

        verify(mantenimientoRepository).save(resultado);
        verify(mantenimientoRepository.save(manteCaptor.capture()));
    }

    @Test
    void buscarPorIdyretornarElId() {
        LocalDate fechaInicioProgramada = LocalDate.now();
        LocalDate fechaFinProgramada = LocalDate.now();

        Respaldo respaldo = new Respaldo();

        int idNOexistente = 99;
        Mantenimiento mante = new Mantenimiento(1, fechaInicioProgramada, fechaFinProgramada, TipoEstado.EN_PROCESO,
                true, respaldo);

        when(mantenimientoRepository.findById(1)).thenReturn(mante);
        when(mantenimientoRepository.findById(idNOexistente)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mantenimientoService.findById(idNOexistente);
        });

        Mantenimiento resultado = mantenimientoService.findById(1);

        assertNotNull(resultado);
        assertEquals(TipoEstado.EN_PROCESO, resultado.getEstado());
        assertEquals(1, resultado.getIdventana());
        assertEquals("No encontrado", ex.getMessage());

        verify(mantenimientoRepository).findById(1);
    }

    @Test
    void activarMantenimientoYretoranTrue() {
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setRespaldoActivo(false);
        mantenimiento.activarRespaldo();
        assertTrue(mantenimiento.isRespaldoActivo());
    }

    @Test
    void desactivarMantenimientoYretornaFalse() {
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setRespaldoActivo(true);
        mantenimiento.desactivarRespaldo();
        assertFalse(mantenimiento.isRespaldoActivo());
    }

    @Test
    void listaMantenimientoYdebeRetornarLalista() {

        LocalDate fechaInicioProgramada = LocalDate.now();
        LocalDate fechaFinProgramada = LocalDate.now();
        int idNOexistente = 100;

        Respaldo respaldo = new Respaldo();

        List<Mantenimiento> listmante = Arrays.asList(
                new Mantenimiento(1, fechaInicioProgramada, fechaFinProgramada, TipoEstado.COMPLETA, false, respaldo),
                new Mantenimiento(2, fechaInicioProgramada, fechaFinProgramada, TipoEstado.EN_PROCESO, true, respaldo));
        when(mantenimientoRepository.findAll()).thenReturn(listmante);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> mantenimientoService.findById(idNOexistente));

        List<Mantenimiento> resultado = mantenimientoService.findAll();

        assertNotNull(resultado);
        assertEquals(listmante, resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
        assertEquals("No encontrado", ex.getMessage());

        assertEquals(1, resultado.get(0).getIdventana());
        verify(mantenimientoRepository).findAll();
    }

    @Test
    void actualizarMantennimientoYretornarNuevoMantentinimiento() {
        LocalDate fechaInicioProgramada = LocalDate.now();
        LocalDate fechaFinProgramada = LocalDate.now();

        Respaldo respaldo = new Respaldo();

        Mantenimiento mantenimientoExistente = new Mantenimiento(1, fechaInicioProgramada, fechaFinProgramada,
                TipoEstado.COMPLETA, false, respaldo);
        Mantenimiento mantenimientoActualizado = new Mantenimiento(1, fechaInicioProgramada, fechaFinProgramada,
                TipoEstado.EN_PROCESO, true, respaldo);

        when(mantenimientoRepository.findById(1)).thenReturn(mantenimientoExistente);
        when(mantenimientoRepository.save(any(Mantenimiento.class))).thenReturn(mantenimientoExistente);

        boolean resultado = mantenimientoService.updateMantenimiento(1, mantenimientoActualizado);

        verify(mantenimientoRepository).findById(1);
        assertTrue(resultado);
        verify(mantenimientoRepository).save(manteCaptor.capture());

        Mantenimiento guardado = manteCaptor.getValue();
        assertEquals(TipoEstado.EN_PROCESO, guardado.getEstado());
        assertFalse(guardado.isRespaldoActivo());
    }

}
