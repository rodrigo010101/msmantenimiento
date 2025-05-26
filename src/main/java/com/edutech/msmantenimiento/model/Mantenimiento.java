package com.edutech.msmantenimiento.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mantenimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idventana;

    @Column(nullable = false)
    private LocalDate fechaInicioProgramada;

    @Column(nullable = false)
    private LocalDate fechaFinProgramada;

    @Enumerated(EnumType.STRING)
    private enumEstado estado;

    public enum enumEstado {
        COMPLETA, EN_PROCESO, FALLIDO
    }

}
