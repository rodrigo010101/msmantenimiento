package com.edutech.msmantenimiento.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "respaldo")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Respaldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrespaldo;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(name = "tamano", nullable = false, precision = 10, scale = 2)
    private BigDecimal tamano;

    @Enumerated
    private enumEstado estado;

    public enum enumEstado {
        COMPLETA, EN_PROCESO, FALLIDO
    }

}
