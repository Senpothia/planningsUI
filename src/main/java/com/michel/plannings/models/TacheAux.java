package com.michel.plannings.models;

import java.time.LocalDateTime;



public class TacheAux {

	private Integer id;
	private Integer numero;
	private LocalDateTime debut;
	private String debutString;
	private LocalDateTime fin;
	private String finString;
	private String heureDebut;
	private String heureFin;
	private String texte;
	private String commentaire;
	private Integer urgence; // dégré d'urgence 1 à 3
	private Boolean actif;
	private String actifString;
	private Boolean suspendu;
	private String suspenduString;
	private UtilisateurAux ressource;

	public TacheAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TacheAux(Integer id, Integer numero, LocalDateTime debut, String debutString, LocalDateTime fin,
			String finString, String heureDebut, String heurefin, String texte, String commentaire, Integer urgence,
			Boolean actif, Boolean suspendu, UtilisateurAux ressource) {
		super();
		this.id = id;
		this.numero = numero;
		this.debut = debut;
		this.debutString = debutString;
		this.fin = fin;
		this.finString = finString;
		this.heureDebut = heureDebut;
		this.heureFin = heurefin;
		this.texte = texte;
		this.commentaire = commentaire;
		this.urgence = urgence;
		this.actif = actif;
		this.suspendu = suspendu;
		this.ressource = ressource;
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

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Integer getUrgence() {
		return urgence;
	}

	public void setUrgence(Integer urgence) {
		this.urgence = urgence;
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

	public UtilisateurAux getRessource() {
		return ressource;
	}

	public void setRessource(UtilisateurAux ressource) {
		this.ressource = ressource;
	}

	public String getDebutString() {
		return debutString;
	}

	public void setDebutString(String debutString) {
		this.debutString = debutString;
	}

	public String getFinString() {
		return finString;
	}

	public void setFinString(String finString) {
		this.finString = finString;
	}

	public String getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}

	public String getActifString() {
		return actifString;
	}

	public void setActifString(String actifString) {
		this.actifString = actifString;
	}

	public String getSuspenduString() {
		return suspenduString;
	}

	public void setSuspenduString(String suspenduString) {
		this.suspenduString = suspenduString;
	}

	

}
