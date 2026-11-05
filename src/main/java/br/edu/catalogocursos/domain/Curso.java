package br.edu.catalogocursos.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelCurso nivel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Instrutor instrutor;

    protected Curso() {
    }

    public Curso(String titulo, Integer cargaHoraria, NivelCurso nivel, Instrutor instrutor) {
        this.titulo = validarTitulo(titulo);
        this.cargaHoraria = validarCargaHoraria(cargaHoraria);
        this.nivel = Objects.requireNonNull(nivel, "Nível é obrigatório");
        Objects.requireNonNull(instrutor, "Instrutor é obrigatório").adicionarCurso(this);
    }

    void associarInstrutor(Instrutor novoInstrutor) {
        Objects.requireNonNull(novoInstrutor, "Instrutor é obrigatório");
        if (instrutor != null && instrutor != novoInstrutor) {
            throw new IllegalStateException("Curso já está associado a outro instrutor");
        }
        instrutor = novoInstrutor;
    }

    private static String validarTitulo(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }

        String titulo = valor.trim();
        if (titulo.length() < 3 || titulo.length() > 150) {
            throw new IllegalArgumentException("Título deve possuir entre 3 e 150 caracteres");
        }
        return titulo;
    }

    private static Integer validarCargaHoraria(Integer valor) {
        if (valor == null || valor < 1 || valor > 400) {
            throw new IllegalArgumentException("Carga horária deve estar entre 1 e 400 horas");
        }
        return valor;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public NivelCurso getNivel() {
        return nivel;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }
}
