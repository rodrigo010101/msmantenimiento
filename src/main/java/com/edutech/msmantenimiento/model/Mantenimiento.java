package com.edutech.msmantenimiento.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mantenimiento")

public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idventana;

    @Column(nullable = false)
    private LocalDate fechaInicioProgramada;

    @Column(nullable = false)
    private LocalDate fechaFinProgramada;

    @Enumerated(EnumType.STRING)
    private TipoEstado estado;

    public enum TipoEstado {
        COMPLETA, EN_PROCESO, FALLIDO
    }

    private boolean respaldoActivo;

    @OneToOne
    private Respaldo respaldo;

    public Mantenimiento(Respaldo respaldo) {
        this.respaldo = respaldo;
        this.respaldoActivo = true;
    }

    public void desactivarRespaldo() {
        this.respaldoActivo = false;
    }

    public void activarRespaldo() {
        this.respaldoActivo = true;
    }

    public void setRespaldoActivo(boolean respaldoActivo) {
        this.respaldoActivo = respaldoActivo;
    }

}
