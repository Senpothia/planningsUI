package com.michel.plannings.models;

import java.time.LocalDateTime;
import java.util.List;

public class ProjetAux {

	private Integer id;
	private String nom;
	private String numero;
	private String type; // Verrouillage, contrôle d'accès
	private Boolean statut;
	private String statutString;
	private LocalDateTime date;
	private String dateString;
	private Integer chefId;
	private String nomChef;
	private Boolean affecte;
	private Boolean gantt;

	public ProjetAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjetAux(Integer id, String nom, String numero, String type, Boolean statut, String statutString,
			LocalDateTime date, String dateString, Integer chefId, String nomChef, Boolean affecte, Boolean gantt) {
		super();
		this.id = id;
		this.nom = nom;
		this.numero = numero;
		this.type = type;
		this.statut = statut;
		this.statutString = statutString;
		this.date = date;
		this.dateString = dateString;
		this.chefId = chefId;
		this.nomChef = nomChef;
		this.affecte = affecte;
		this.gantt = gantt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getChefId() {
		return chefId;
	}

	public void setChefId(Integer chefId) {
		this.chefId = chefId;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getNomChef() {
		return nomChef;
	}

	public void setNomChef(String nomChef) {
		this.nomChef = nomChef;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public Boolean getAffecte() {
		return affecte;
	}

	public void setAffecte(Boolean affecte) {
		this.affecte = affecte;
	}

	public String getStatutString() {
		return statutString;
	}

	public void setStatutString(String statutString) {
		this.statutString = statutString;
	}

	public Boolean getGantt() {
		return gantt;
	}

	public void setGantt(Boolean gantt) {
		this.gantt = gantt;
	}

}
