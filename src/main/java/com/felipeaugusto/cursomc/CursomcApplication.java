package com.felipeaugusto.cursomc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.felipeaugusto.cursomc.domain.Categoria;
import com.felipeaugusto.cursomc.domain.Produto;
import com.felipeaugusto.cursomc.repositories.CategoriaRepository;
import com.felipeaugusto.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	@Autowired
	private CategoriaRepository crepo;
	@Autowired
	private ProdutoRepository prepo;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio"); 
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		List<Categoria> c = new ArrayList<Categoria>();
		
		c.add(cat1);
		c.add(cat2);
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		List<Produto> p = new ArrayList<Produto>();
		p.add(p1);
		p.add(p2);
		p.add(p3);

		crepo.saveAll(c);
		prepo.saveAll(p);
		}
}
