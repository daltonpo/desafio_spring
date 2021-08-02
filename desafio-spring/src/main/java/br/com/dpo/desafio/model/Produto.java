package br.com.dpo.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Entity(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
	private String name;
	
	@Column(nullable = false )
	@NotBlank(message = "Description is mandatory")
	private String description;
	
	@Column(nullable = false)
	@Positive(message = "The Prince must be positive")
	private double prince;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrince() {
		return prince;
	}
	public void setPrince(double prince) {
		this.prince = prince;
	}
		
}
