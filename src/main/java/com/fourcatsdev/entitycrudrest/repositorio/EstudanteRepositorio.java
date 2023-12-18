package com.fourcatsdev.entitycrudrest.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourcatsdev.entitycrudrest.modelo.Estudante;

public interface EstudanteRepositorio extends JpaRepository<Estudante, Long> {

}
