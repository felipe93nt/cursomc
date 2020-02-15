package com.felipeaugusto.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.felipeaugusto.cursomc.domain.Cliente;
import com.felipeaugusto.cursomc.services.ClienteServices;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteServices clienteServices;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> findClienteById(@PathVariable Integer id){
		Cliente c = clienteServices.findCliente(id);
		return ResponseEntity.ok().body(c);
	}

}
