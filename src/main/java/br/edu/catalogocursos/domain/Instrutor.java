package br.edu.catalogocursos.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "instrutor")
public class Instrutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 160, unique = true)
    private String email;

    @OneToMany(mappedBy = "instrutor", fetch = FetchType.LAZY)
    private List<Curso> cursos = new ArrayList<>();

    protected Instrutor() {
    }

    public Instrutor(String nome, String email) {
        this.nome = validarTexto(nome, "nome", 3, 120);
        this.email = validarTexto(email, "email", 3, 160).toLowerCase(Locale.ROOT);
    }

    public void adicionarCurso(Curso curso) {
        Objects.requireNonNull(curso, "Curso não pode ser nulo");
        curso.associarInstrutor(this);

        boolean jaAdicionado = cursos.stream().anyMatch(cursoExistente -> cursoExistente == curso);
        if (!jaAdicionado) {
            cursos.add(curso);
        }
    }

    private static String validarTexto(String valor, String campo, int minimo, int maximo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " é obrigatório");
        }

        String texto = valor.trim();
        if (texto.length() < minimo || texto.length() > maximo) {
            throw new IllegalArgumentException(campo + " deve possuir entre " + minimo + " e " + maximo + " caracteres");
        }
        return texto;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Curso> getCursos() {
        return List.copyOf(cursos);
    }
}
