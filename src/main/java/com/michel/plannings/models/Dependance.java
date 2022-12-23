package com.michel.plannings.models;

public class Dependance {

	private Integer id;

	private Integer antecedente; // id de la dépendance
	private Integer suivante; // id de la phase considérée relativement à la dépendance

	public Dependance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Dependance(Integer id, Integer antecedente, Integer suivante) {
		super();
		this.id = id;
		this.antecedente = antecedente;
		this.suivante = suivante;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAntecedente() {
		return antecedente;
	}

	public void setAntecedente(Integer antecedente) {
		this.antecedente = antecedente;
	}

	public Integer getSuivante() {
		return suivante;
	}

	public void setSuivante(Integer suivante) {
		this.suivante = suivante;
	}

	@Override
	public String toString() {
		return "Dependance [id=" + id + ", antecedente=" + antecedente + ", suivante=" + suivante + "]";
	}
	
	

}
