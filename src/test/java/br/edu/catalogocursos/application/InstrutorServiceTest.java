package br.edu.catalogocursos.application;

import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.Instrutor;
import br.edu.catalogocursos.domain.NivelCurso;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstrutorServiceTest {

    @Mock
    private InstrutorRepository instrutorRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private InstrutorService instrutorService;

    @Test
    void deveListarSomenteCursosDoInstrutorOrdenadosPorTitulo() {
        Instrutor instrutor = new Instrutor("Marina Costa", "marina.costa@example.com");
        Curso api = new Curso("APIs REST", 40, NivelCurso.INTERMEDIARIO, instrutor);
        Curso java = new Curso("Java orientado a objetos", 60, NivelCurso.INICIANTE, instrutor);
        when(instrutorRepository.existsById(1L)).thenReturn(true);
        when(cursoRepository.findByInstrutorIdOrderByTituloAsc(1L)).thenReturn(List.of(api, java));

        List<Curso> cursos = instrutorService.listarCursos(1L);

        assertEquals(List.of("APIs REST", "Java orientado a objetos"),
                cursos.stream().map(Curso::getTitulo).toList());
        verify(cursoRepository).findByInstrutorIdOrderByTituloAsc(1L);
    }
}
