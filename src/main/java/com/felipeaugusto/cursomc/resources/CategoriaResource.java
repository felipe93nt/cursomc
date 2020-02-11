package com.felipeaugusto.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.felipeaugusto.cursomc.domain.Categoria;
import com.felipeaugusto.cursomc.services.CategoriaServices;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaServices service;
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria c =  service.buscar(id);
	return ResponseEntity.ok().body(c);
	}

}
