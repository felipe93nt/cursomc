package com.felipeaugusto.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.felipeaugusto.cursomc.domain.Cliente;
import com.felipeaugusto.cursomc.dto.ClienteDTO;
import com.felipeaugusto.cursomc.dto.ClienteNewDTO;
import com.felipeaugusto.cursomc.services.ClienteServices;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteServices clienteServices;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> findClienteById(@PathVariable Integer id) {
		Cliente c = clienteServices.find(id);
		return ResponseEntity.ok().body(c);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO cliente) {
		Cliente cli = clienteServices.fromDTO(cliente);
		cli = clienteServices.insert(cli);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cli.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO cliente, @PathVariable Integer id) {
		Cliente cli = clienteServices.fromDTO(cliente);
		cli.setId(id);
		cli = clienteServices.update(cli);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) {
		clienteServices.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> c = clienteServices.findAll();
		List<ClienteDTO> Cliente = c.stream().map(cli -> new ClienteDTO(cli)).collect(Collectors.toList());
		return ResponseEntity.ok().body(Cliente);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> c = clienteServices.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> Cliente = c.map(cli -> new ClienteDTO(cli));
		return ResponseEntity.ok().body(Cliente);
	}
}
