package com.felipeaugusto.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipeaugusto.cursomc.domain.Categoria;
import com.felipeaugusto.cursomc.domain.Produto;
import com.felipeaugusto.cursomc.repositories.CategoriaRepository;
import com.felipeaugusto.cursomc.repositories.ProdutoRepository;
import com.felipeaugusto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository repo;

	public Produto find(Integer id) {
		Optional<Produto> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado para este ID:" + id));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer page,Integer linesPerPage,String orderBy, String direction){
		PageRequest pr = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pr);
	}
}
