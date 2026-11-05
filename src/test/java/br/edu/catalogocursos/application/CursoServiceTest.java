package br.edu.catalogocursos.application;

import br.edu.catalogocursos.domain.NivelCurso;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private InstrutorRepository instrutorRepository;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void deveRejeitarCadastroComInstrutorInexistente() {
        when(instrutorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> cursoService.cadastrar("Java moderno", 32, NivelCurso.INICIANTE, 999L)
        );
        verifyNoInteractions(cursoRepository);
    }
}
