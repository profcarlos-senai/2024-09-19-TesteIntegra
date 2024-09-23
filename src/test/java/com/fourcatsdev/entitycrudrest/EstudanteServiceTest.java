package com.fourcatsdev.entitycrudrest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import com.fourcatsdev.entitycrudrest.excecao.EstudanteNotFoundException;
import com.fourcatsdev.entitycrudrest.modelo.Estudante;
import com.fourcatsdev.entitycrudrest.repositorio.EstudanteRepositorio;
import com.fourcatsdev.entitycrudrest.servico.EstudanteServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EstudanteServiceTest {

    @Mock
    private EstudanteRepositorio estudanteRepository;

    @InjectMocks
    private EstudanteServico estudanteService;

    private Estudante estudante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        estudante = new Estudante(1L, "Carlos", 20);
    }

    @Test
    void testSaveEstudante() {
        when(estudanteRepository.save(any(Estudante.class)))
                .thenReturn(estudante);

        Estudante savedEstudante = estudanteService.gravar(estudante);

        assertNotNull(savedEstudante);
        assertEquals(estudante.getId(), savedEstudante.getId());
        assertEquals(estudante.getNome(), savedEstudante.getNome());
        assertEquals(estudante.getIdade(), savedEstudante.getIdade());
    }

    @Test

    void testUpdateEstudante() throws EstudanteNotFoundException {
        estudante.setNome(estudante.getNome()+" da Silva");
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));
        when(estudanteRepository.save(any(Estudante.class))).thenReturn(estudante);

        Estudante savedEstudante = estudanteService.alterarEstudante(estudante.getId(), estudante);

        assertNotNull(savedEstudante);
        assertEquals(estudante.getId(), savedEstudante.getId());
        assertEquals(estudante.getNome(), savedEstudante.getNome());
        assertEquals(estudante.getIdade(), savedEstudante.getIdade());
    }


    @Test
    void testFindById() throws EstudanteNotFoundException {
        when(estudanteRepository.findById(1L))
                .thenReturn(Optional.of(estudante));

        Estudante foundEstudante = estudanteService.buscarEstudantePorId(1L);

        assertNotNull(foundEstudante);
        assertEquals("Carlos", foundEstudante.getNome());
    }

    @Test
    void testFindAll() {
        List<Estudante> estudantes = Arrays.asList(estudante, new Estudante(2L, "Ana", 22));
        when(estudanteRepository.findAll()).thenReturn(estudantes);

        List<Estudante> allEstudantes = estudanteService.buscarTodos();

        assertEquals(2, allEstudantes.size());
    }

    @Test
    void testDeleteEstudante() throws EstudanteNotFoundException {
        when(estudanteRepository.findById(1L))
                .thenReturn(Optional.of(estudante));
        doNothing().when(estudanteRepository).deleteById(1L);

        estudanteService.apagarEstudante(1L);

        verify(estudanteRepository, times(1)).delete(estudante);
    }
}
