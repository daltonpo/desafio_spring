package br.com.dpo.desafio.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.dpo.desafio.model.Produto;

@Repository
public class ProdutoCustonRepository {

	@Autowired
	private EntityManager em;
	
	public List<Produto> search(String description, Double min_price, Double max_price) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Produto> produto = cq.from(Produto.class);
        
        if (description!= null && !description.isEmpty()) {        
        	Predicate namePredicate = cb.like(produto.get("name"), "%" + description + "%");
        	Predicate descriptionPredicate = cb.like(produto.get("description"), "%" + description + "%");
        	predicates.add(cb.or(namePredicate, descriptionPredicate));
        }
        
        if (min_price != null) {
        	Predicate princePredicate = cb.greaterThanOrEqualTo(produto.get("prince"), min_price);
        	predicates.add(cb.and(princePredicate));
        }
        
        if (max_price != null) {
        	Predicate princePredicate = cb.lessThanOrEqualTo(produto.get("prince"), max_price);
        	predicates.add(cb.and(princePredicate));        	
        }
        
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Produto> query = em.createQuery(cq);
        return query.getResultList();
    }
}
