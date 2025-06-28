package com.edutech.msmantenimiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.msmantenimiento.model.Respaldo;
import com.edutech.msmantenimiento.model.Respaldo.enumEstado;
import com.edutech.msmantenimiento.respository.RespaldoRepository;

@ExtendWith(MockitoExtension.class)

public class RespaldoServiceTest {

    @Mock
    private RespaldoRepository respaldoRepository;

    @InjectMocks
    private RespaldoService respaldoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    ArgumentCaptor<Respaldo> capturador;

    @Captor
    ArgumentCaptor<Integer> idCaptor;

    @Test
    void guardarElRespaldoYdebeRetornarelGuardado() {

        Respaldo respaldo = new Respaldo();
        respaldo.setIdrespaldo(1);

        when(respaldoRepository.save(respaldo)).thenReturn(respaldo);
        when(respaldoRepository.findById(1)).thenReturn(Optional.of(respaldo));

        Respaldo guardado = respaldoService.save(respaldo);
        assertNotNull(guardado);
        assertEquals(1, guardado.getIdrespaldo());

        Optional<Respaldo> encontrado = respaldoService.findById(1);
        assertTrue(encontrado.isPresent());
        assertNotNull(encontrado);
        assertEquals(guardado, encontrado.get());
        assertEquals(1, encontrado.get().getIdrespaldo());

        Respaldo capturado = capturador.getValue();
        assertEquals(encontrado, capturado);
        assertEquals(1, capturado.getIdrespaldo());

    }

    @Test
    void buscarPorIdYretornarElObjetoPorid() {

        Respaldo respaldo = new Respaldo();
        respaldo.setIdrespaldo(1);

        when(respaldoRepository.findById(anyInt())).thenReturn(Optional.of(new Respaldo()));

        respaldoService.findById(1);

        verify(respaldoRepository).findById(idCaptor.capture());
        Integer capturado = idCaptor.getValue();

        assertEquals(1, capturado);
    }

    @Test
    void listarTodosLosRespaldoDebeRetornarLalista() {

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now();

        Respaldo respaldo = new Respaldo(1, fechaInicio, fechaFin, new BigDecimal("10000.00"), enumEstado.COMPLETA,
                true);
        Respaldo respaldo1 = new Respaldo(2, fechaInicio, fechaFin, new BigDecimal("20000.00"), enumEstado.EN_PROCESO,
                true);
        Respaldo respaldo2 = new Respaldo(3, fechaInicio, fechaFin, new BigDecimal("30000.00"), enumEstado.FALLIDO,
                true);

        List<Respaldo> listRespaldo = new ArrayList<>(Arrays.asList(respaldo, respaldo1, respaldo2));

        when(respaldoRepository.findAll()).thenReturn(listRespaldo);

        List<Respaldo> resultado = respaldoService.findAllList();

        assertEquals(listRespaldo, resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(listRespaldo.size(), resultado.size());
        assertEquals(3, resultado.size());

        verify(respaldoRepository).findAll();
    }

    @Test
    void eliminarRespaldoporId() {
        doNothing().when(respaldoRepository).deleteById(1);
        respaldoService.deleteById(1);
        verify(respaldoRepository).deleteById(1);
    }

    @Test
    void desactivarRespaldoYdebeRetornarTrue() {

        Respaldo respaldo = new Respaldo();

        respaldo.setHabilitado(false);
        respaldo.deshabilitado();
        assertFalse(respaldo.isHabilitado());

        when(respaldoRepository.save(any(Respaldo.class))).thenReturn(respaldo);
        respaldoService.save(respaldo);
        verify(respaldoRepository).save(capturador.capture());
        assertEquals(respaldo, capturador.getValue());

    }

    @Test
    void activarRespaldoDebeRetornarFalse() {
        Respaldo respaldo = new Respaldo();
        respaldo.setHabilitado(true);
        respaldo.deshabilitado();
        assertFalse(respaldo.isHabilitado());

        when(respaldoRepository.save(any(Respaldo.class))).thenReturn(respaldo);

        respaldoService.save(respaldo);
        verify(respaldoRepository).save(capturador.capture());
        assertEquals(respaldo, capturador.getValue());

    }

}
