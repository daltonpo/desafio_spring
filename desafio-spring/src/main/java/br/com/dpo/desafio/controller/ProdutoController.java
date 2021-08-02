package br.com.dpo.desafio.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dpo.desafio.model.Produto;
import br.com.dpo.desafio.repository.ProdutoCustonRepository;
import br.com.dpo.desafio.repository.ProdutoRepository;

@RestController
@RequestMapping("/products")
public class ProdutoController {
	
	@Autowired
	ProdutoRepository repository;
	
	@Autowired
	ProdutoCustonRepository custonRepository;
	
	@GetMapping
	public List<Produto> findAll(){
	   return repository.findAll();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable(value = "id") String id){		
		return repository.findById(id)
		           .map(record -> ResponseEntity.ok().body(record))
		           .orElse(ResponseEntity.notFound().build());
    }
	
	@GetMapping("/search")
    public ResponseEntity<List<Produto>> getProdutos(@RequestParam(name = "q", required = false) String q,
            								  @RequestParam(name = "min_price", required = false) String min_price,
            								  @RequestParam(name = "max_price", required = false) String max_price){		
		return ResponseEntity.ok().body(custonRepository.search(q,Double.valueOf(min_price),Double.valueOf(max_price)));
    }
	
	@PostMapping
	public ResponseEntity<Produto> create(@Valid @Validated @RequestBody Produto produto){
	   return new ResponseEntity<Produto>(repository.save(produto), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = {"/{id}"})
	public ResponseEntity<Object> delete(@PathVariable String id){
		return repository.findById(id)
		           .map(record -> {
		               repository.deleteById(id);
		               return ResponseEntity.ok().build();
		           }).orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping(path = {"/{id}"})
	public ResponseEntity<Produto> update(@PathVariable String id, @RequestBody @Valid Produto produto){
		return repository.findById(id)
		           .map(record -> {
		        	   produto.setId(id);
		               repository.save(produto);
		               return ResponseEntity.ok().body(produto);
		           }).orElse(ResponseEntity.notFound().build());
	}
}
