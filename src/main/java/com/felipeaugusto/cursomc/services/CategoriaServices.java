package com.felipeaugusto.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipeaugusto.cursomc.domain.Categoria;
import com.felipeaugusto.cursomc.dto.CategoriaDTO;
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
		Categoria cat = find(categoria.getId());
		updateData(cat, categoria);
		return repo.save(cat);
	}
	
	private void updateData(Categoria cat, Categoria categoria) {
		cat.setNome(categoria.getNome());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possivel excluir esta categoria, devido a existência de produtos!");
		}
	}

	public List<Categoria> findAll() {
		List<Categoria> c = repo.findAll();
		if(c.size() > 0) {
			return c;
		}
		return c;
	}
	
	public Page<Categoria> findPage(Integer page,Integer linesPerPage,String orderBy, String direction){
		PageRequest pr = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pr);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDto) {
		return new Categoria(categoriaDto.getId(),categoriaDto.getNome());
	}
}
