package com.felipeaugusto.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeaugusto.cursomc.domain.Cliente;
import com.felipeaugusto.cursomc.repositories.ClienteRepository;
import com.felipeaugusto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteServices {

	@Autowired
	private ClienteRepository clienteRepo;

	public Cliente findCliente(Integer id) {
		Optional<Cliente> c = clienteRepo.findById(id);

		return c.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado para o Id: " + id));
	}
}
