package com.fourcatsdev.entitycrudrest.controle;

import java.util.List;

import com.fourcatsdev.entitycrudrest.modelo.Estudante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourcatsdev.entitycrudrest.dto.EstudanteCreateDTO;
import com.fourcatsdev.entitycrudrest.dto.EstudanteUpdateDTO;
import com.fourcatsdev.entitycrudrest.dto.EstudanteResponseDTO;
import com.fourcatsdev.entitycrudrest.dto.mapper.EstudanteMapper;
import com.fourcatsdev.entitycrudrest.excecao.EstudanteNotFoundException;
import com.fourcatsdev.entitycrudrest.servico.EstudanteServico;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estudantes")
public class EstudanteControle {

	@Autowired
	private EstudanteServico estudanteServico;

	@Autowired
	private EstudanteMapper estudanteMapper;

	@PostMapping
	public ResponseEntity<EstudanteResponseDTO> salvar(@RequestBody @Valid EstudanteCreateDTO estudanteCreateDTO) {
		Estudante estudante = estudanteMapper.toEntity(estudanteCreateDTO);
		Estudante estudanteGravado = estudanteServico.gravar(estudante);
		EstudanteResponseDTO estudanteResponseDTO = estudanteMapper.toDTO(estudanteGravado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estudanteResponseDTO);
	}

	@GetMapping
	public ResponseEntity<List<EstudanteResponseDTO>> buscarTodos() {
		List<Estudante> estudantes = estudanteServico.buscarTodos();
		List<EstudanteResponseDTO> estudantesResponseDTO = estudanteMapper.toDTO(estudantes);
		return ResponseEntity.status(HttpStatus.OK).body(estudantesResponseDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarUm(@PathVariable(value = "id") Long id) throws EstudanteNotFoundException {

		Estudante estudanteGravado = estudanteServico.buscarEstudantePorId(id);
		EstudanteResponseDTO estudanteResponseDTO = estudanteMapper.toDTO(estudanteGravado);
		return ResponseEntity.status(HttpStatus.OK).body(estudanteResponseDTO);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> alterar(@PathVariable(value = "id") Long id,
			@RequestBody @Valid EstudanteUpdateDTO estudanteUpdateDTO) throws EstudanteNotFoundException {
		Estudante estudante = estudanteMapper.toEntity(estudanteUpdateDTO);
		estudante.setId(id);
		Estudante estudanteGravado = estudanteServico.alterarEstudante(id, estudante);
		EstudanteResponseDTO estudanteResponseDTO = estudanteMapper.toDTO(estudanteGravado);
		return ResponseEntity.status(HttpStatus.OK).body(estudanteResponseDTO);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> apagar(@PathVariable(value = "id") Long id) throws EstudanteNotFoundException {
		estudanteServico.apagarEstudante(id);
		return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso.");
	}

}
