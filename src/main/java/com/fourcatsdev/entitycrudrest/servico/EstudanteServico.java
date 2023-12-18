package com.fourcatsdev.entitycrudrest.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourcatsdev.entitycrudrest.excecao.EstudanteNotFoundException;
import com.fourcatsdev.entitycrudrest.modelo.Estudante;
import com.fourcatsdev.entitycrudrest.repositorio.EstudanteRepositorio;

@Service
public class EstudanteServico {
	
	@Autowired
	private EstudanteRepositorio estudanteRepositorio;
	
	public Estudante gravar(Estudante estudante) {
		return estudanteRepositorio.save(estudante);
	}
	
	public List<Estudante> buscarTodos(){
		return estudanteRepositorio.findAll();
	}
	
	public Estudante buscarEstudantePorId(Long id) throws EstudanteNotFoundException {
		Optional<Estudante> opt = estudanteRepositorio.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new EstudanteNotFoundException("Estudante com id : " + id + " não existe");
		}		
	}
	
	public Estudante alterarEstudante(Long id, Estudante estudante) throws EstudanteNotFoundException {
		Estudante estudanteGravado = buscarEstudantePorId(id);
		estudanteGravado.setIdade(estudante.getIdade());
		estudanteGravado.setNome(estudante.getNome());
		return estudanteRepositorio.save(estudanteGravado);
	}
	
	public void apagarEstudante(Long id) throws EstudanteNotFoundException {
		Estudante estudante = buscarEstudantePorId(id);
		estudanteRepositorio.delete(estudante);
	}

}
