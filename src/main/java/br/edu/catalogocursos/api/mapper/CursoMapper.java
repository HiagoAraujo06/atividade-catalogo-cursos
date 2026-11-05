package br.edu.catalogocursos.api.mapper;

import br.edu.catalogocursos.api.dto.CursoResponse;
import br.edu.catalogocursos.api.dto.CursoResumoResponse;
import br.edu.catalogocursos.domain.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    private final InstrutorMapper instrutorMapper;

    public CursoMapper(InstrutorMapper instrutorMapper) {
        this.instrutorMapper = instrutorMapper;
    }

    public CursoResponse paraResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getCargaHoraria(),
                curso.getNivel(),
                instrutorMapper.paraResumo(curso.getInstrutor())
        );
    }

    public CursoResumoResponse paraResumo(Curso curso) {
        return new CursoResumoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getCargaHoraria(),
                curso.getNivel()
        );
    }
}
