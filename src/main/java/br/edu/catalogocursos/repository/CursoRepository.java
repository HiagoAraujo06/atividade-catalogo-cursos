package br.edu.catalogocursos.repository;

import br.edu.catalogocursos.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByInstrutorIdOrderByTituloAsc(Long instrutorId);

    @Query("select c from Curso c join fetch c.instrutor where c.id = :id")
    Optional<Curso> buscarDetalhadoPorId(@Param("id") Long id);
}
