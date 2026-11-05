package br.edu.catalogocursos.api;

import br.edu.catalogocursos.api.controller.InstrutorController;
import br.edu.catalogocursos.api.exception.ApiExceptionHandler;
import br.edu.catalogocursos.api.mapper.CursoMapper;
import br.edu.catalogocursos.api.mapper.InstrutorMapper;
import br.edu.catalogocursos.application.InstrutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InstrutorControllerTest {

    @Mock
    private InstrutorService instrutorService;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        InstrutorMapper instrutorMapper = new InstrutorMapper();
        CursoMapper cursoMapper = new CursoMapper(instrutorMapper);
        InstrutorController controller = new InstrutorController(instrutorService, instrutorMapper, cursoMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void deveRetornar400AoCadastrarInstrutorInvalido() throws Exception {
        mockMvc.perform(post("/api/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "A",
                                  "email": "email-invalido"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Dados de entrada inválidos"))
                .andExpect(jsonPath("$.path").value("/api/instrutores"));

        verifyNoInteractions(instrutorService);
    }
}
