package com.felipeaugusto.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeaugusto.cursomc.domain.Categoria;
import com.felipeaugusto.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaServices {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> c =  repo.findById(id);
		return c.orElse(null);
	}
}
