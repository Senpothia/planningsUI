package com.michel.plannings.models;

import java.time.LocalDateTime;
import java.util.List;

public class PhaseAux {

	private Integer id;
	private Integer numero;
	private ProjetAux projet;
	private String nom; // nom de la phase
	private LocalDateTime debut;
	private LocalDateTime fin;
	private UtilisateurAux ressource;
	private String description;
	private String complement; // Complément d'information: échantillons, version, etc.
	private String resultat;
	private Boolean conforme;
	private Boolean actif;
	private Boolean suspendu;
	private List<FicheAux> fiches;

	public PhaseAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhaseAux(Integer id, Integer numero, ProjetAux projet, String nom, LocalDateTime debut, LocalDateTime fin,
			UtilisateurAux ressource, String description, String complement, String resultat, Boolean conforme,
			Boolean actif, Boolean suspendu, List<FicheAux> fiches) {
		super();
		this.id = id;
		this.numero = numero;
		this.projet = projet;
		this.nom = nom;
		this.debut = debut;
		this.fin = fin;
		this.ressource = ressource;
		this.description = description;
		this.complement = complement;
		this.resultat = resultat;
		this.conforme = conforme;
		this.actif = actif;
		this.suspendu = suspendu;
		this.fiches = fiches;
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

	public ProjetAux getProjet() {
		return projet;
	}

	public void setProjet(ProjetAux projet) {
		this.projet = projet;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDateTime getDebut() {
		return debut;
	}

	public void setDebut(LocalDateTime debut) {
		this.debut = debut;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	public UtilisateurAux getRessource() {
		return ressource;
	}

	public void setRessource(UtilisateurAux ressource) {
		this.ressource = ressource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Boolean getSuspendu() {
		return suspendu;
	}

	public void setSuspendu(Boolean suspendu) {
		this.suspendu = suspendu;
	}

	public List<FicheAux> getFiches() {
		return fiches;
	}

	public void setFiches(List<FicheAux> fiches) {
		this.fiches = fiches;
	}

	public Boolean getConforme() {
		return conforme;
	}

	public void setConforme(Boolean conforme) {
		this.conforme = conforme;
	}

}
