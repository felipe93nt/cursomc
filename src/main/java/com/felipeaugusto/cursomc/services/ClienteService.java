package com.felipeaugusto.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipeaugusto.cursomc.domain.Cidade;
import com.felipeaugusto.cursomc.domain.Cliente;
import com.felipeaugusto.cursomc.domain.Endereco;
import com.felipeaugusto.cursomc.domain.enums.Perfil;
import com.felipeaugusto.cursomc.domain.enums.TipoCliente;
import com.felipeaugusto.cursomc.dto.ClienteDTO;
import com.felipeaugusto.cursomc.dto.ClienteNewDTO;
import com.felipeaugusto.cursomc.repositories.ClienteRepository;
import com.felipeaugusto.cursomc.repositories.EnderecoRepository;
import com.felipeaugusto.cursomc.security.UserSS;
import com.felipeaugusto.cursomc.services.exceptions.AuthorizationException;
import com.felipeaugusto.cursomc.services.exceptions.DataIntegrityException;
import com.felipeaugusto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
				
		Optional<Cliente> c = clienteRepo.findById(id);

		return c.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado para o Id: " + id));
	}

	public Cliente update(Cliente cliente) {
		Cliente cli = find(cliente.getId());
		updateData(cli, cliente);
		return clienteRepo.save(cli);
	}

	private void updateData(Cliente cli, Cliente cliente) {
		cli.setNome(cliente.getNome());
		cli.setEmail(cliente.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possivel excluir este Cliente, devido a existência de pedidos!");
		}
	}

	public List<Cliente> findAll() {
		List<Cliente> c = clienteRepo.findAll();
		if (c.size() > 0) {
			return c;
		}
		return c;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pr = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepo.findAll(pr);
	}

	public Cliente insert(Cliente cli) {
		cli.setId(null);
		cli = clienteRepo.save(cli);
		enderecoRepo.saveAll(cli.getEnderecos());
		return cli;
	}

	public Cliente fromDTO(@Valid ClienteDTO cliente) {
		return new Cliente(cliente.getId(), cliente.getNome(), cliente.getEmail(), null, null,null);
	}

	public Cliente fromDTO(@Valid ClienteNewDTO cliente) {
		Cliente cli = new Cliente(null, cliente.getNome(), cliente.getEmail(), cliente.getCpfOuCnpj(),
				TipoCliente.toEnum(cliente.getTipo()), pe.encode(cliente.getSenha()));
		Cidade c = new Cidade(cliente.getCidadeId(), null, null);
		Endereco end = new Endereco(null, cliente.getLogradouro(), cliente.getNumero(), cliente.getComplemento(),
				cliente.getBairro(), cliente.getCep(), cli, c);
		cli.getEnderecos().add(end);

		cli.getTelefones().add(cliente.getTelefone1());
		if (cliente.getTelefone2() != null) {
			cli.getTelefones().add(cliente.getTelefone2());
		}
		if (cliente.getTelefone3() != null) {
			cli.getTelefones().add(cliente.getTelefone3());
		}
		return cli;
	}

}
