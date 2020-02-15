package com.felipeaugusto.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeaugusto.cursomc.domain.Pedido;
import com.felipeaugusto.cursomc.repositories.PedidoRepository;
import com.felipeaugusto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		Optional<Pedido> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado para este ID:" + id));
	}
}
