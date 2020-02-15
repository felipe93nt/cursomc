package com.felipeaugusto.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.felipeaugusto.cursomc.domain.Categoria;
import com.felipeaugusto.cursomc.repositories.CategoriaRepository;
import com.felipeaugusto.cursomc.services.exceptions.DataIntegrityException;
import com.felipeaugusto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaServices {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado para este ID:" + id));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repo.save(categoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possivel excluir esta categoria, devido a existência de produtos!");
		}
	}
}
