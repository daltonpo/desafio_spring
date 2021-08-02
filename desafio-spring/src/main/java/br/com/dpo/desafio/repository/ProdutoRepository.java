package br.com.dpo.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dpo.desafio.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, String>, JpaSpecificationExecutor<Produto>{

}
