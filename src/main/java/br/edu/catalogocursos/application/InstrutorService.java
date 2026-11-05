package br.edu.catalogocursos.application;

import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.Instrutor;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final CursoRepository cursoRepository;

    public InstrutorService(InstrutorRepository instrutorRepository, CursoRepository cursoRepository) {
        this.instrutorRepository = instrutorRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public Instrutor cadastrar(String nome, String email) {
        if (instrutorRepository.existsByEmailIgnoreCase(email)) {
            throw new RecursoDuplicadoException("Já existe um instrutor cadastrado com este e-mail");
        }
        return instrutorRepository.save(new Instrutor(nome, email));
    }

    @Transactional(readOnly = true)
    public Instrutor buscarPorId(Long id) {
        return instrutorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Instrutor não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Curso> listarCursos(Long instrutorId) {
        if (!instrutorRepository.existsById(instrutorId)) {
            throw new RecursoNaoEncontradoException("Instrutor não encontrado");
        }
        return cursoRepository.findByInstrutorIdOrderByTituloAsc(instrutorId);
    }
}
