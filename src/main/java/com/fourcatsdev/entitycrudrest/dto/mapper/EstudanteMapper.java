package com.fourcatsdev.entitycrudrest.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.fourcatsdev.entitycrudrest.dto.EstudanteUpdateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fourcatsdev.entitycrudrest.dto.EstudanteCreateDTO;
import com.fourcatsdev.entitycrudrest.dto.EstudanteResponseDTO;
import com.fourcatsdev.entitycrudrest.modelo.Estudante;

@Component
public class EstudanteMapper {

	@Autowired
	private ModelMapper mapper;
	
	public Estudante toEntity(EstudanteCreateDTO dto) {
		Estudante entity = mapper.map(dto, Estudante.class);
		return entity;
	}

	public Estudante toEntity(EstudanteUpdateDTO dto) {
		Estudante entity = mapper.map(dto, Estudante.class);
		return entity;
	}
	
	public EstudanteResponseDTO toDTO(Estudante entity) {
		EstudanteResponseDTO dto = mapper.map(entity, EstudanteResponseDTO.class);
		return dto;
	}
	
	public List<EstudanteResponseDTO> toDTO(List<Estudante> estudantes) {
		return estudantes.stream()
        		.map(estudante -> toDTO(estudante))
                .collect(Collectors.toList());
    }
	
}
