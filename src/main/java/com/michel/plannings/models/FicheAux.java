package com.michel.plannings.models;

import java.time.LocalDateTime;

public class FicheAux {
	
	private Integer id;
	private Integer numero;
	private LocalDateTime date;
	private UtilisateurAux auteur;
	private String anomalie;
	private Boolean statut; // ouverte, fermée
	private Integer niveau;
	private PhaseAux phase;
	
	public FicheAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public FicheAux(Integer id, Integer numero, LocalDateTime date, UtilisateurAux auteur, String anomalie,
			Boolean statut, Integer niveau, PhaseAux phase) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.auteur = auteur;
		this.anomalie = anomalie;
		this.statut = statut;
		this.niveau = niveau;
		this.phase = phase;
	}



	



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getNumero() {
		return numero;
	}



	public void setNumero(Integer numero) {
		this.numero = numero;
	}



	public LocalDateTime getDate() {
		return date;
	}



	public void setDate(LocalDateTime date) {
		this.date = date;
	}



	public UtilisateurAux getAuteur() {
		return auteur;
	}



	public void setAuteur(UtilisateurAux auteur) {
		this.auteur = auteur;
	}



	public String getAnomalie() {
		return anomalie;
	}



	public void setAnomalie(String anomalie) {
		this.anomalie = anomalie;
	}



	public Boolean getStatut() {
		return statut;
	}



	public void setStatut(Boolean statut) {
		this.statut = statut;
	}



	public Integer getNiveau() {
		return niveau;
	}



	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}



	public PhaseAux getPhase() {
		return phase;
	}



	public void setPhase(PhaseAux phase) {
		this.phase = phase;
	}




	
	
	

}
